package com.shopeasy.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.shopeasy.app.data.SeedData
import com.shopeasy.app.data.entity.Product
import com.shopeasy.app.data.local.AppDatabase
import com.shopeasy.app.data.repository.ProductRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ProductViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val repo = ProductRepository(db)

    // Текущий фильтр категории
    private val _selectedCategory = MutableStateFlow("Все")
    val selectedCategory: StateFlow<String> = _selectedCategory

    // Поисковый запрос
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    @OptIn(ExperimentalCoroutinesApi::class)
    val products: StateFlow<List<Product>> = combine(
        _selectedCategory, _searchQuery
    ) { category, query ->
        Pair(category, query)
    }.flatMapLatest { (category, query) ->
        when {
            query.isNotBlank() -> repo.searchProducts(query)
            category == "Все" -> repo.getAllProducts()
            else -> repo.getProductsByCategory(category)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        seedDataIfNeeded()
    }

    private fun seedDataIfNeeded() {
        viewModelScope.launch {
            if (repo.getCount() == 0) {
                repo.insertAll(SeedData.getSeedProducts())
            }
        }
    }

    fun setCategory(category: String) {
        _selectedCategory.value = category
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    suspend fun getProductById(id: Int): Product? = repo.getProductById(id)

    fun addProduct(product: Product) {
        viewModelScope.launch { repo.insert(product) }
    }

    fun updateProduct(product: Product) {
        viewModelScope.launch { repo.update(product) }
    }

    fun deleteProduct(product: Product) {
        viewModelScope.launch { repo.delete(product) }
    }
}