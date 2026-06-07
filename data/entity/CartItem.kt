package com.shopeasy.app.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val productId: Int,
    val name: String,
    val price: Double,
    val quantity: Int = 1,
    val userId: String
)