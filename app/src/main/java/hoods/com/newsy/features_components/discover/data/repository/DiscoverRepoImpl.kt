package hoods.com.newsy.features_components.discover.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import hoods.com.newsy.features_components.core.data.local.NewsyArticleDatabase
import hoods.com.newsy.features_components.core.domain.mapper.Mapper
import hoods.com.newsy.features_components.core.domain.models.NewsyArticle
import hoods.com.newsy.features_components.discover.data.local.models.DiscoveryArticleDto
import hoods.com.newsy.features_components.discover.data.paging.DiscoverMediator
import hoods.com.newsy.features_components.discover.data.remote.DiscoverApi
import hoods.com.newsy.features_components.discover.domain.repository.DiscoveryRepository
import hoods.com.newsy.utils.K
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.internal.cacheGet

class DiscoverRepoImpl(
    private val discoverApi: DiscoverApi,
    private val database: NewsyArticleDatabase,
    private val mapper: Mapper<DiscoveryArticleDto, NewsyArticle>,
) : DiscoveryRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun fetchDiscoverArticles(
        category: String,
        country: String,
        language: String
    ): Flow<PagingData<NewsyArticle>> {
        return Pager(
            PagingConfig(
                pageSize = K.PAGE_SIZE,
                initialLoadSize = 10,
                prefetchDistance = K.PAGE_SIZE - 1
            ),
            pagingSourceFactory = {
                database.discoverArticleDao().getDiscoverArticleDataSource(category)
            },

            remoteMediator = DiscoverMediator(
                api = discoverApi,
                database=database,
                category=category,
                country=country,
                language=language
            )

        ).flow.map { dtoPager->
        dtoPager.map {dto->
            mapper.toModel(dto)
        }

        }
    }

    override suspend fun updateCategory(category: String) {
      database.discoverRemoteKeyDao().updateCategory(category)
    }

    override suspend fun getDiscoverCurrentCategory(): String {
        return  database.discoverRemoteKeyDao().getCurrentCategory()
    }

    override suspend fun updateFavouriteDiscoverCategory(article: NewsyArticle) {
        database.discoverArticleDao().updateFavouriteArticle(
            isFavourite = article.favourite,
            id=article.id

        )
    }
}