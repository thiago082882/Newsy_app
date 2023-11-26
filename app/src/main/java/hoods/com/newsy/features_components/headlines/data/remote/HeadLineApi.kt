package hoods.com.newsy.features_components.headlines.data.remote

import hoods.com.newsy.features_components.core.data.remote.models.NewsRemoteDto
import hoods.com.newsy.utils.K
import retrofit2.http.GET
import retrofit2.http.Query

interface HeadLineApi {
    companion object{
        private  const val HEADLINE_END_POINT = "/v2/top-headlines"
    }

    @GET(HEADLINE_END_POINT)
    suspend fun  getHeadlines(
        @Query("apikey") key:String = K.API_KEY,
        @Query("category") category : String,
        @Query("country") country: String,
        @Query("language")language: String,
        @Query("page")page: String,
        @Query("pageSize")pageSize: String

    ): NewsRemoteDto
}