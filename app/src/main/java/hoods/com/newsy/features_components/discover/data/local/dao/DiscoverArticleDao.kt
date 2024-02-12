package hoods.com.newsy.features_components.discover.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import hoods.com.newsy.features_components.discover.data.local.models.DiscoveryArticleDto
import kotlinx.coroutines.flow.Flow


@Dao
interface DiscoverArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllArticles(list :List<DiscoveryArticleDto>)
    @Query("SELECT * FROM discovery_article WHERE category=:category")
    fun getDiscoverArticleDataSource(category: String): PagingSource<Int, DiscoveryArticleDto>

    @Query("SELECT * FROM discovery_article WHERE id=:id")
    fun getDiscoverArticle(id:Int): Flow<DiscoveryArticleDto>

    @Query("DELETE FROM DISCOVERY_ARTICLE WHERE favourite=0 AND category=:category")
    suspend fun removeAllDiscoverArticles(category:String)

    @Delete
    suspend fun  removeFavouriteArticle(discoveryArticleDto: DiscoveryArticleDto)

  @Query("UPDATE discovery_article SET favourite=:isFavourite WHERE id=:id")
    suspend fun  updateFavouriteArticle(isFavourite:Boolean,id:Int):Int

}