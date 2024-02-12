package hoods.com.newsy.features_components.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import hoods.com.newsy.features_components.discover.data.local.dao.DiscoverArticleDao
import hoods.com.newsy.features_components.discover.data.local.dao.DiscoverRemoteKeyDao
import hoods.com.newsy.features_components.discover.data.local.models.DiscoverKeys
import hoods.com.newsy.features_components.discover.data.local.models.DiscoveryArticleDto
import hoods.com.newsy.features_components.headlines.data.local.dao.HeadlineDao
import hoods.com.newsy.features_components.headlines.data.local.dao.HeadlineRemoteKeyDao
import hoods.com.newsy.features_components.headlines.data.local.model.HeadlineDto
import hoods.com.newsy.features_components.headlines.data.local.model.HeadlineRemoteKey

@Database(
    entities = [
        HeadlineDto::class,
        HeadlineRemoteKey::class,
        DiscoveryArticleDto::class,
        DiscoverKeys::class
    ], exportSchema = false, version = 1
)
abstract class NewsyArticleDatabase : RoomDatabase() {
    abstract fun headlineDao(): HeadlineDao
    abstract fun headlineRemoteDao(): HeadlineRemoteKeyDao
    abstract fun discoverArticleDao(): DiscoverArticleDao
    abstract fun discoverRemoteKeyDao(): DiscoverRemoteKeyDao
}