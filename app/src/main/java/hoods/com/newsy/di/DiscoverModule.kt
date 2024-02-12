package hoods.com.newsy.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hoods.com.newsy.features_components.core.data.local.NewsyArticleDatabase
import hoods.com.newsy.features_components.core.domain.mapper.Mapper
import hoods.com.newsy.features_components.core.domain.models.NewsyArticle
import hoods.com.newsy.features_components.discover.data.local.dao.DiscoverArticleDao
import hoods.com.newsy.features_components.discover.data.local.dao.DiscoverRemoteKeyDao
import hoods.com.newsy.features_components.discover.data.local.models.DiscoveryArticleDto
import hoods.com.newsy.features_components.discover.data.mapper.DiscoverMapper
import hoods.com.newsy.features_components.discover.data.remote.DiscoverApi
import hoods.com.newsy.features_components.discover.data.repository.DiscoverRepoImpl
import hoods.com.newsy.features_components.discover.domain.repository.DiscoveryRepository
import hoods.com.newsy.utils.K
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DiscoverModule {


    private val json = Json {
        coerceInputValues = true
        ignoreUnknownKeys = true
    }

    @Singleton
    @Provides
    fun provideDiscoverApi(): DiscoverApi {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(K.BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(DiscoverApi::class.java)

    }

    @Singleton
    @Provides
    fun provideDiscoverRepository(
        api : DiscoverApi,
        database: NewsyArticleDatabase,
        mapper: Mapper<DiscoveryArticleDto,NewsyArticle>

    ):DiscoveryRepository{
        return  DiscoverRepoImpl(
            discoverApi = api,
            database=database,
            mapper=mapper
        )

    }
    @Singleton
    @Provides
    fun provideDiscoverArticleDao(
        database: NewsyArticleDatabase
    ): DiscoverArticleDao = database.discoverArticleDao()

    @Singleton
    @Provides
    fun provideRemoteKeyDao(database:NewsyArticleDatabase):DiscoverRemoteKeyDao
    = database.discoverRemoteKeyDao()

    @Singleton
    @Provides
    fun provideDiscoverMapper(): Mapper<DiscoveryArticleDto,NewsyArticle>
    =DiscoverMapper()

}