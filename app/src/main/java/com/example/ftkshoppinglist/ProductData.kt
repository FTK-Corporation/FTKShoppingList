package com.example.ftkshoppinglist

data class ProductData(
    val id: String,
    val name: String,
    val description: String,
    val imageUri: String,
    val aisle: String? = null,
    val category: String? = null,
    val subCategory: String? = null,
    val price: Int? = null
)
