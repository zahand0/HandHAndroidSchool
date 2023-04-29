package com.zahand0.cowboys.data.repository

import android.accounts.AuthenticatorException
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.zahand0.cowboys.data.remote.CowboysApi
import com.zahand0.cowboys.data.remote.paging_source.GetOrdersSource
import com.zahand0.cowboys.data.remote.paging_source.GetProductsSource
import com.zahand0.cowboys.data.toOrderModel
import com.zahand0.cowboys.data.toProductDetailsModel
import com.zahand0.cowboys.data.toProductModel
import com.zahand0.cowboys.data.toUserModel
import com.zahand0.cowboys.domain.model.OrderModel
import com.zahand0.cowboys.domain.model.ProductDetailsModel
import com.zahand0.cowboys.domain.model.ProductModel
import com.zahand0.cowboys.domain.model.UserModel
import com.zahand0.cowboys.domain.repository.DataStoreOperations
import com.zahand0.cowboys.domain.repository.Repository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val cowboysApi: CowboysApi,
    private val dataStore: DataStoreOperations
) : Repository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getProducts(): Flow<PagingData<ProductModel>> =
        flow {
            val token = getToken()

            emit(
                Pager(
                    PagingConfig(pageSize = 10, initialLoadSize = 20)
                ) {
                    GetProductsSource(cowboysApi, token)
                }.flow
            )
        }.flatMapLatest { it }

    override suspend fun getProductDetails(productId: String): Result<ProductDetailsModel> {
        try {
            val bearerToken = getToken() ?: return Result.failure(AuthenticatorException())
            val response = cowboysApi.getProduct(bearerToken, productId)
            return Result.success(response.data.toProductDetailsModel())
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun signIn(login: String, password: String): Result<UserModel> {
        val credentials = mapOf("email" to login, "password" to password)
        return try {
            val response = cowboysApi.signIn(credentials)
            dataStore.saveAccessToken(response.data.accessToken)
            Result.success(response.data.profile.toUserModel())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signOut() {}

    override suspend fun getUser(): Result<UserModel> {
        try {
            val bearerToken = getToken() ?: return Result.failure(AuthenticatorException())
            val response = cowboysApi.getUser(bearerToken)
            return Result.success(response.data.profile.toUserModel())
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    private suspend fun getToken(): String? {
        val token = dataStore.readAccessToken().first() ?: return null
        return "Bearer $token"
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getOrders(): Flow<PagingData<OrderModel>> =
        flow {
            val token = getToken()

            emit(
                Pager(
                    PagingConfig(pageSize = 10, initialLoadSize = 20)
                ) {
                    GetOrdersSource(cowboysApi, token)
                }.flow
            )
        }.flatMapLatest { it }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getActiveOrders(): Flow<PagingData<OrderModel>> =
        flow {
            val token = getToken()

            emit(
                Pager(
                    PagingConfig(pageSize = 10, initialLoadSize = 20)
                ) {
                    GetOrdersSource(cowboysApi, token, true)
                }.flow
            )
        }.flatMapLatest { it }

    override suspend fun cancelOrder(orderId: String): Result<OrderModel> {
        try {
            val bearerToken = getToken() ?: return Result.failure(AuthenticatorException())
            val response = cowboysApi.cancelOrder(bearerToken, orderId)
            val product =
                cowboysApi.getProduct(bearerToken, response.data.productId).data.toProductModel()
            return Result.success(response.data.toOrderModel(product))
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

}