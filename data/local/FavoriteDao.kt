package com.shopeasy.app.data.local

import androidx.room.*
import com.shopeasy.app.data.entity.Favorite
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: Favorite)

    @Delete
    suspend fun delete(favorite: Favorite)

    @Query("SELECT * FROM favorites WHERE userId = :userId")
    fun getFavorites(userId: String): Flow<List<Favorite>>

    @Query("SELECT * FROM favorites WHERE productId = :productId AND userId = :userId LIMIT 1")
    suspend fun getFavorite(productId: Int, userId: String): Favorite?

    @Query("DELETE FROM favorites WHERE productId = :productId AND userId = :userId")
    suspend fun removeByProductAndUser(productId: Int, userId: String)
}