package com.zahand0.cowboys.domain.repository

import com.zahand0.cowboys.domain.model.Product
import com.zahand0.cowboys.domain.model.User

interface Repository {

    suspend fun getProducts(): Result<List<Product>>
    suspend fun signIn(login: String, password: String): Result<User>
    suspend fun signOut()
    suspend fun getUser(): Result<User>
}