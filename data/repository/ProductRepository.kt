package com.shopeasy.app.data.repository

import com.shopeasy.app.data.entity.Product
import com.shopeasy.app.data.local.AppDatabase
import kotlinx.coroutines.flow.Flow

class ProductRepository(private val db: AppDatabase) {

    private val productDao = db.productDao()

    fun getAllProducts(): Flow<List<Product>> = productDao.getAllProducts()

    fun getProductsByCategory(category: String): Flow<List<Product>> =
        productDao.getProductsByCategory(category)

    fun searchProducts(query: String): Flow<List<Product>> =
        productDao.searchProducts(query)

    suspend fun getProductById(id: Int): Product? = productDao.getProductById(id)

    suspend fun insert(product: Product) = productDao.insert(product)

    suspend fun insertAll(products: List<Product>) = productDao.insertAll(products)

    suspend fun update(product: Product) = productDao.update(product)

    suspend fun delete(product: Product) = productDao.delete(product)

    suspend fun getCount(): Int = productDao.getCount()
}