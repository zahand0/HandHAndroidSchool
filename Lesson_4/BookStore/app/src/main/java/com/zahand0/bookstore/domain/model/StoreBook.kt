package com.zahand0.bookstore.domain.model

data class StoreBook(
    val bookId: String,
    val authorId: String,
    val name: String,
    val inStock: Boolean
)
