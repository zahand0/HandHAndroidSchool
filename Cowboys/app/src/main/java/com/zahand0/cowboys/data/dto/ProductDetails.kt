package com.zahand0.cowboys.data.dto

data class ProductDetails(
    val id: String,
    val title: String,
    val department: String,
    val price: Int,
    val badge: List<Badge>,
    val preview: String,
    val images: List<String>,
    val sizes: List<ProductSize>,
    val description: String,
    val details: List<String>
)
