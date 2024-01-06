package hoods.com.newsy.features_components.headlines.data.paging

import android.net.http.HttpException
import androidx.annotation.RequiresApi
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import hoods.com.newsy.features_components.core.data.local.NewsyArticleDatabase
import hoods.com.newsy.features_components.core.data.remote.models.Article
import hoods.com.newsy.features_components.core.domain.mapper.Mapper
import hoods.com.newsy.features_components.headlines.data.local.model.ArticleHeadlineDtoMapper
import hoods.com.newsy.features_components.headlines.data.local.model.HeadlineDto
import hoods.com.newsy.features_components.headlines.data.local.model.HeadlineRemoteKey
import hoods.com.newsy.features_components.headlines.data.remote.HeadLineApi
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class HeadlineRemoteMediator(
    private val api: HeadLineApi,
    private val database: NewsyArticleDatabase,
    private val articleHeadlineDtoMapper: Mapper<Article, HeadlineDto>,
    private val category: String = "",
    private val country: String = "",
    private val language: String = ""
) : RemoteMediator<Int, HeadlineDto>() {

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(20, TimeUnit.MINUTES)
        return if (
            System.currentTimeMillis() -
            (database.headlineRemoteDao().getCreationTime() ?: 0) < cacheTimeout
        ) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    @RequiresApi(34)
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, HeadlineDto>
    ): MediatorResult {
        val page: Int = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKey = getRemoteKeyClosestToCurrentPosition(state)
                remoteKey?.nextKey?.minus(1) ?: 1
            }
            LoadType.PREPEND -> {
                val remoteKey = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKey?.prevKey
                prevKey ?: return MediatorResult.Success(
                    endOfPaginationReached = remoteKey != null
                )
            }
            LoadType.APPEND -> {
                val remoteKey = getRemoteKeyForLastItem(state)
                val nextKey = remoteKey?.nextKey
                nextKey ?: return  MediatorResult.Success(
                    endOfPaginationReached = remoteKey != null
                )
            }
        }

        return try {

            val headlineApiResponse = api.getHeadlines(
                pageSize = state.config.pageSize.toString(),
                category = category,
                page = page,
                country = country,
                language = language
            )
            val headlineArticles = headlineApiResponse.articles
            val endOfPaginationReached = headlineArticles.isEmpty()
            database.apply {
                if (loadType == LoadType.REFRESH) {
                    database.apply {
                        headlineRemoteDao().clearRemoteKeys()
                        headlineDao().removeAllHeadlineArticles()

                    }
                }
                val prevKey = if (page > 1) page - 1 else null
                val nextKey = if (endOfPaginationReached) null else page + 1
                val remoteKeys = headlineArticles.map {
                    HeadlineRemoteKey(
                        articleId = it.url,
                        prevKey = prevKey,
                        nextKey = nextKey,
                        currentPage = page
                    )
                }
                database.apply {
                    headlineRemoteDao().insertAll(remoteKeys)
                    headlineDao().insertHeadlineArticles(
                        articles = headlineArticles.map {
                            articleHeadlineDtoMapper.toModel(
                                it
                            )
                        }
                    )
                }
            }

            MediatorResult.Success(endOfPaginationReached)
        } catch (e: IOException) {

            MediatorResult.Error(e)

        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, HeadlineDto>
    ): HeadlineRemoteKey? {
        return state.pages.firstOrNull() {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { article ->
            database.headlineRemoteDao().getRemoteKeyByArticleId(article.url)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, HeadlineDto>
    ): HeadlineRemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.url?.let { id ->
                database.headlineRemoteDao().getRemoteKeyByArticleId(id)

            }
        }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, HeadlineDto>
    ): HeadlineRemoteKey? {
        return state.pages.lastOrNull() {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { article ->
            database.headlineRemoteDao().getRemoteKeyByArticleId(article.url)

        }
    }
}
