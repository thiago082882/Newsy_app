package hoods.com.newsy.features_components.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import hoods.com.newsy.features_components.headlines.data.local.dao.HeadlineDao
import hoods.com.newsy.features_components.headlines.data.local.dao.HeadlineRemoteKeyDao

@Database(
    entities = [
        HeadlineDao::class,
        HeadlineRemoteKeyDao::class
    ], exportSchema = false, version = 1
)
abstract class NewsyArticleDatabase : RoomDatabase() {
    abstract fun headlineDao(): HeadlineDao
    abstract fun headlineRemoteDao(): HeadlineRemoteKeyDao
}