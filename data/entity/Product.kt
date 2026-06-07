package com.shopeasy.app.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val description: String,
    val price: Double,
    val category: String,       // "Одежда" или "Продукты"
    val imageUrl: String,
    val rating: Float = 0f,
    val inStock: Boolean = true
)