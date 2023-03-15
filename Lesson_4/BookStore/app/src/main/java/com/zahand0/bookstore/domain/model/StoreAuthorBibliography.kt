package com.zahand0.bookstore.domain.model

data class StoreAuthorBibliography(
    val authorId: String,
    val authorName: String,
    val books: List<StoreBook>
)
