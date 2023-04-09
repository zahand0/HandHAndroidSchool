package com.zahand0.cowboys.domain.model

data class ProductDetails(
    val id: String,
    val title: String,
    val category: String,
    val price: Int,
    val badge: Badge,
    val preview: String,
    val images: List<String>,
    val sizes: List<ProductSize>,
    val description: String,
    val details: List<String>
)
