package com.zahand0.cowboys.domain.model

data class Product(
    val id: String,
    val title: String,
    val category: String,
    val price: Int,
    val preview: String
)
