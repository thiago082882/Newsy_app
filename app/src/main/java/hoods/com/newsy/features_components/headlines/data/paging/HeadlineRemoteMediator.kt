package hoods.com.newsy.features_components.headlines.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import hoods.com.newsy.features_components.core.data.local.NewsyArticleDatabase
import hoods.com.newsy.features_components.headlines.data.local.model.HeadlineDto
import hoods.com.newsy.features_components.headlines.data.remote.HeadLineApi
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class HeadlineRemoteMediator(
    private val api: HeadLineApi,
    private val database: NewsyArticleDatabase,
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

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, HeadlineDto>
    ): MediatorResult {
        TODO("Not yet implemented")
    }
}