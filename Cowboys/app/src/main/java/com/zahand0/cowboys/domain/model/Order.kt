package com.zahand0.cowboys.domain.model

data class Order(
    val id: String,
    val number: Int,
    val product: Product,
    val productPreview: String,
    val productQuantity: Int,
    val productSize: String,
    val createdAt: String,
    val etd: String,
    val deliveryAddress: String,
    val status: String
)