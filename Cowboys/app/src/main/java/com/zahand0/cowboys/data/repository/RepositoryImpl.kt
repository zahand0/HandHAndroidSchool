package com.zahand0.cowboys.data.repository

import android.accounts.AuthenticatorException
import com.zahand0.cowboys.data.remote.CowboysApi
import com.zahand0.cowboys.domain.model.Order
import com.zahand0.cowboys.domain.model.Product
import com.zahand0.cowboys.domain.model.ProductDetails
import com.zahand0.cowboys.domain.model.User
import com.zahand0.cowboys.domain.repository.DataStoreOperations
import com.zahand0.cowboys.domain.repository.Repository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val cowboysApi: CowboysApi,
    private val dataStore: DataStoreOperations
) : Repository {
    override suspend fun getProducts(): Result<List<Product>> {
        TODO("Not yet implemented")
    }

    override suspend fun getProductDetails(productId: String): Result<ProductDetails> {
        TODO("Not yet implemented")
    }

    override suspend fun signIn(login: String, password: String): Result<User> {
        val credentials = mapOf("email" to login, "password" to password)
        return try {
            val response = cowboysApi.signIn(credentials)
            dataStore.saveAccessToken(response.data.accessToken)
            Result.success(response.data.profile.toUser())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signOut() {

    }

    override suspend fun getUser(): Result<User> {
        try {
            val token = dataStore.readAccessToken().first() ?: return Result.failure(
                AuthenticatorException()
            )
            val bearerToken = "Bearer $token"
            val response = cowboysApi.getUser(bearerToken)
            return Result.success(response.data.profile.toUser())
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun getOrders(): Result<List<Order>> {
        TODO("Not yet implemented")
    }

    override suspend fun getActiveOrders(): Result<List<Order>> {
        TODO("Not yet implemented")
    }

    override suspend fun cancelOrder(orderId: String): Result<Order> {
        TODO("Not yet implemented")
    }
}