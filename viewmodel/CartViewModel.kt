package com.shopeasy.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.shopeasy.app.data.entity.CartItem
import com.shopeasy.app.data.entity.Favorite
import com.shopeasy.app.data.local.AppDatabase
import com.shopeasy.app.data.repository.CartRepository
import com.shopeasy.app.util.Notifier
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CartViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val repo = CartRepository(db)
    private val context = application

    // ---- Корзина ----

    private val _userId = MutableStateFlow("")
    val userId: StateFlow<String> = _userId

    val cartItems: StateFlow<List<CartItem>> = _userId.flatMapLatest { uid ->
        if (uid.isNotBlank()) repo.getCartItems(uid) else flowOf(emptyList())
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val cartTotal: StateFlow<Double> = _userId.flatMapLatest { uid ->
        if (uid.isNotBlank()) repo.getTotal(uid).map { it ?: 0.0 } else flowOf(0.0)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    fun setUserId(uid: String) {
        _userId.value = uid
    }

    fun addToCart(productId: Int, name: String, price: Double) {
        viewModelScope.launch {
            val uid = _userId.value
            val existing = repo.getCartItem(productId, uid)
            if (existing != null) {
                repo.updateCartItem(existing.copy(quantity = existing.quantity + 1))
            } else {
                repo.addToCart(
                    CartItem(
                        productId = productId,
                        name = name,
                        price = price,
                        quantity = 1,
                        userId = uid
                    )
                )
            }
            Notifier.sendAddToCartNotification(context, name, price)
        }
    }

    fun increaseQuantity(item: CartItem) {
        viewModelScope.launch {
            repo.updateCartItem(item.copy(quantity = item.quantity + 1))
        }
    }

    fun decreaseQuantity(item: CartItem) {
        viewModelScope.launch {
            if (item.quantity > 1) {
                repo.updateCartItem(item.copy(quantity = item.quantity - 1))
            } else {
                repo.removeFromCart(item)
            }
        }
    }

    fun removeFromCart(item: CartItem) {
        viewModelScope.launch { repo.removeFromCart(item) }
    }

    fun clearCart() {
        viewModelScope.launch { repo.clearCart(_userId.value) }
    }

    fun checkout() {
        viewModelScope.launch {
            val total = cartTotal.value
            Notifier.sendOrderNotification(context, total)
            repo.clearCart(_userId.value)
        }
    }

    // ---- Избранное ----

    val favorites: StateFlow<List<Favorite>> = _userId.flatMapLatest { uid ->
        if (uid.isNotBlank()) repo.getFavorites(uid) else flowOf(emptyList())
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun toggleFavorite(productId: Int) {
        viewModelScope.launch {
            val uid = _userId.value
            if (repo.isFavorite(productId, uid)) {
                repo.removeFromFavorites(productId, uid)
            } else {
                repo.addToFavorites(Favorite(productId = productId, userId = uid))
            }
        }
    }

    fun isFavorite(productId: Int): Boolean {
        val uid = _userId.value
        return favorites.value.any { it.productId == productId && it.userId == uid }
    }
}