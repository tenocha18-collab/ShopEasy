package com.shopeasy.app.data.repository

import com.shopeasy.app.data.entity.CartItem
import com.shopeasy.app.data.entity.Favorite
import com.shopeasy.app.data.local.AppDatabase
import kotlinx.coroutines.flow.Flow

class CartRepository(private val db: AppDatabase) {

    private val cartDao = db.cartDao()
    private val favoriteDao = db.favoriteDao()

    // ---- Корзина ----

    fun getCartItems(userId: String): Flow<List<CartItem>> = cartDao.getCartItems(userId)

    fun getTotal(userId: String): Flow<Double?> = cartDao.getTotal(userId)

    suspend fun addToCart(item: CartItem) = cartDao.insert(item)

    suspend fun updateCartItem(item: CartItem) = cartDao.update(item)

    suspend fun removeFromCart(item: CartItem) = cartDao.delete(item)

    suspend fun clearCart(userId: String) = cartDao.clearCart(userId)

    suspend fun getCartItem(productId: Int, userId: String): CartItem? =
        cartDao.getCartItem(productId, userId)

    // ---- Избранное ----

    fun getFavorites(userId: String): Flow<List<Favorite>> = favoriteDao.getFavorites(userId)

    suspend fun addToFavorites(favorite: Favorite) = favoriteDao.insert(favorite)

    suspend fun removeFromFavorites(productId: Int, userId: String) =
        favoriteDao.removeByProductAndUser(productId, userId)

    suspend fun isFavorite(productId: Int, userId: String): Boolean =
        favoriteDao.getFavorite(productId, userId) != null
}