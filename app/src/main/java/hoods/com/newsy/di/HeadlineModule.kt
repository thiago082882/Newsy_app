package hoods.com.newsy.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hoods.com.newsy.features_components.core.data.local.NewsyArticleDatabase
import hoods.com.newsy.features_components.core.data.remote.models.Article
import hoods.com.newsy.features_components.core.domain.mapper.Mapper
import hoods.com.newsy.features_components.core.domain.models.NewsyArticle
import hoods.com.newsy.features_components.headlines.data.local.dao.HeadlineDao
import hoods.com.newsy.features_components.headlines.data.local.dao.HeadlineRemoteKeyDao
import hoods.com.newsy.features_components.headlines.data.local.mapper.HeadlineMapper
import hoods.com.newsy.features_components.headlines.data.local.model.ArticleHeadlineDtoMapper
import hoods.com.newsy.features_components.headlines.data.local.model.HeadlineDto
import hoods.com.newsy.features_components.headlines.data.local.model.HeadlineRemoteKey
import hoods.com.newsy.features_components.headlines.data.remote.HeadLineApi
import hoods.com.newsy.features_components.headlines.data.repository.HeadlineRepositoryImpl
import hoods.com.newsy.features_components.headlines.domain.repository.HeadlineRepository
import hoods.com.newsy.features_components.headlines.domain.use_cases.FetchHeadlineArticleUseCase
import hoods.com.newsy.features_components.headlines.domain.use_cases.HeadlineUseCases
import hoods.com.newsy.features_components.headlines.domain.use_cases.UpdateHeadlineFavouriteUseCase
import hoods.com.newsy.utils.K
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object HeadlineModule {


    private val json =  Json {
        coerceInputValues = true
        ignoreUnknownKeys = true
    }
    @Singleton
    @Provides
    fun provideHeadlineApi():HeadLineApi{
        val contentType = "application/json".toMediaType()
        return  Retrofit.Builder()
            .baseUrl(K.BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(HeadLineApi::class.java)

    }

    @Singleton
    @Provides
    fun provideHeadlineRepository(
        api : HeadLineApi,
        database: NewsyArticleDatabase,
        mapper: Mapper<HeadlineDto,NewsyArticle>,
        articleHeadlineMapper: Mapper<Article,HeadlineDto>
    ):HeadlineRepository{
        return  HeadlineRepositoryImpl(
            headLineApi = api,
            database = database,
            mapper = mapper,
            articleHeadlineMapper = articleHeadlineMapper
        )
    }

    @Singleton
    @Provides
    fun provideHeadlineDao(
        database: NewsyArticleDatabase
    ):HeadlineDao = database.headlineDao()


    @Singleton
    @Provides
    fun provideRemoteKeyDao(
        database: NewsyArticleDatabase
    ):HeadlineRemoteKeyDao = database.headlineRemoteDao()


    @Singleton
    @Provides
    fun provideHeadlineUseCases(
        repository: HeadlineRepository
    ):HeadlineUseCases =
        HeadlineUseCases(
            fetchHeadlineArticleUseCase = FetchHeadlineArticleUseCase(repository),
            updateHeadlineFavouriteUseCase = UpdateHeadlineFavouriteUseCase(repository)
        )

    @Singleton
    @Provides
    fun provideArticleToHeadlineMapper():Mapper<Article,HeadlineDto>
    = ArticleHeadlineDtoMapper()


    @Singleton
    @Provides
    fun provideHeadlineMapper():Mapper<HeadlineDto,NewsyArticle>
            = HeadlineMapper()
}