package hoods.com.newsy.features_components.discover.data.remote

import hoods.com.newsy.features_components.core.data.remote.models.NewsRemoteDto
import hoods.com.newsy.utils.K
import retrofit2.http.GET
import retrofit2.http.Query

interface DiscoverApi {
    companion object {
        private const val DISCOVER_END_POINT = "/v2/top-headlines"
    }


    @GET(DISCOVER_END_POINT)
    suspend fun getDiscoverHeadlines(
        @Query("api_key") key: String = K.API_KEY,
        @Query("category") category: String,
        @Query("country") country: String,
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
    ):NewsRemoteDto
}