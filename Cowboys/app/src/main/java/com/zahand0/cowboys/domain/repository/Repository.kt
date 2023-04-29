package com.zahand0.cowboys.domain.repository

import androidx.paging.PagingData
import com.zahand0.cowboys.domain.model.OrderModel
import com.zahand0.cowboys.domain.model.ProductDetailsModel
import com.zahand0.cowboys.domain.model.ProductModel
import com.zahand0.cowboys.domain.model.UserModel
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getProducts(): Flow<PagingData<ProductModel>>
    suspend fun getProductDetails(productId: String): Result<ProductDetailsModel>
    suspend fun signIn(login: String, password: String): Result<UserModel>
    suspend fun signOut()
    suspend fun getUser(): Result<UserModel>
    fun getOrders(): Flow<PagingData<OrderModel>>
    fun getActiveOrders(): Flow<PagingData<OrderModel>>
    suspend fun cancelOrder(orderId: String): Result<OrderModel>
}