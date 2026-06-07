package com.shopeasy.app.data.local

import androidx.room.*
import com.shopeasy.app.data.entity.CartItem
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cartItem: CartItem)

    @Query("SELECT * FROM cart_items WHERE userId = :userId")
    fun getCartItems(userId: String): Flow<List<CartItem>>

    @Query("SELECT * FROM cart_items WHERE productId = :productId AND userId = :userId LIMIT 1")
    suspend fun getCartItem(productId: Int, userId: String): CartItem?

    @Update
    suspend fun update(cartItem: CartItem)

    @Delete
    suspend fun delete(cartItem: CartItem)

    @Query("DELETE FROM cart_items WHERE userId = :userId")
    suspend fun clearCart(userId: String)

    @Query("SELECT SUM(price * quantity) FROM cart_items WHERE userId = :userId")
    fun getTotal(userId: String): Flow<Double?>
}