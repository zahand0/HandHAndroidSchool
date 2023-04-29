package com.zahand0.cowboys.domain.model

data class OrderModel(
    val id: String,
    val number: Int,
    val product: ProductModel,
    val productPreview: String,
    val productQuantity: Int,
    val productSize: String,
    val createdAt: String,
    val etd: String,
    val deliveryAddress: String,
    val status: String
)