package com.shopeasy.app.data

import com.shopeasy.app.data.entity.Product

object SeedData {

    fun getSeedProducts(): List<Product> = listOf(
        // === Одежда ===
        Product(
            id = 1, name = "Футболка классическая",
            description = "Хлопковая футболка, 100% хлопок. Доступна в нескольких цветах.",
            price = 990.0, category = "Одежда",
            imageUrl = "https://placehold.co/400x400/FF6B6B/white?text=T-Shirt",
            rating = 4.5f, inStock = true
        ),
        Product(
            id = 2, name = "Джинсы slim fit",
            description = "Современный крой, ткань с добавлением эластана для комфорта.",
            price = 3490.0, category = "Одежда",
            imageUrl = "https://placehold.co/400x400/4ECDC4/white?text=Jeans",
            rating = 4.7f, inStock = true
        ),
        Product(
            id = 3, name = "Куртка демисезонная",
            description = "Лёгкая куртка для межсезонья. Водонепроницаемая ткань.",
            price = 7990.0, category = "Одежда",
            imageUrl = "https://placehold.co/400x400/45B7D1/white?text=Jacket",
            rating = 4.3f, inStock = true
        ),
        Product(
            id = 4, name = "Кроссовки спортивные",
            description = "Лёгкие кроссовки для бега и повседневной носки.",
            price = 5490.0, category = "Одежда",
            imageUrl = "https://placehold.co/400x400/96CEB4/white?text=Sneakers",
            rating = 4.8f, inStock = true
        ),
        Product(
            id = 5, name = "Шапка вязаная",
            description = "Тёплая вязаная шапка из мериносовой шерсти.",
            price = 1290.0, category = "Одежда",
            imageUrl = "https://placehold.co/400x400/DDA0DD/white?text=Hat",
            rating = 4.2f, inStock = true
        ),

        // === Продукты ===
        Product(
            id = 6, name = "Молоко 3.2%",
            description = "Свежее пастеризованное молоко, 1 литр.",
            price = 89.0, category = "Продукты",
            imageUrl = "https://placehold.co/400x400/FFEAA7/333?text=Milk",
            rating = 4.0f, inStock = true
        ),
        Product(
            id = 7, name = "Хлеб бородинский",
            description = "Тёмный хлеб на закваске, 400 г.",
            price = 65.0, category = "Продукты",
            imageUrl = "https://placehold.co/400x400/F0B27A/white?text=Bread",
            rating = 4.6f, inStock = true
        ),
        Product(
            id = 8, name = "Яблоки Гала",
            description = "Свежие яблоки сорта Гала, 1 кг.",
            price = 120.0, category = "Продукты",
            imageUrl = "https://placehold.co/400x400/58D68D/white?text=Apples",
            rating = 4.4f, inStock = true
        ),
        Product(
            id = 9, name = "Сыр Российский",
            description = "Выдержанный сыр, 200 г. Жирность 50%.",
            price = 280.0, category = "Продукты",
            imageUrl = "https://placehold.co/400x400/F4D03F/333?text=Cheese",
            rating = 4.1f, inStock = true
        ),
        Product(
            id = 10, name = "Куриная грудка",
            description = "Охлаждённая куриная грудка без кожи, 500 г.",
            price = 220.0, category = "Продукты",
            imageUrl = "https://placehold.co/400x400/FAD7A0/333?text=Chicken",
            rating = 4.3f, inStock = true
        )
    )
}