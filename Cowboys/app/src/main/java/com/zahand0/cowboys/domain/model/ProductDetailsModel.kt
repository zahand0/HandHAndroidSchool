package com.zahand0.cowboys.domain.model

data class ProductDetailsModel(
    val id: String,
    val title: String,
    val department: String,
    val price: Int,
    val badge: BadgeModel,
    val preview: String,
    val images: List<String>,
    val sizes: List<ProductSizeModel>,
    val description: String,
    val details: List<String>
)
