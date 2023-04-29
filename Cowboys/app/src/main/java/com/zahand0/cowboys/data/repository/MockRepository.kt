package com.zahand0.cowboys.data.repository

import android.accounts.AuthenticatorException
import androidx.paging.PagingData
import com.zahand0.cowboys.data.StubData
import com.zahand0.cowboys.domain.model.OrderModel
import com.zahand0.cowboys.domain.model.ProductDetailsModel
import com.zahand0.cowboys.domain.model.ProductModel
import com.zahand0.cowboys.domain.model.UserModel
import com.zahand0.cowboys.domain.repository.Repository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MockRepository @Inject constructor() : Repository {

    override fun getProducts(): Flow<PagingData<ProductModel>> {
//        randomDelay()
//        return randomResult(
//            StubData.products
//        )
        return flow { }
    }

    override suspend fun getProductDetails(productId: String): Result<ProductDetailsModel> {
        randomDelay()
        val product = StubData.productDetailsList.find { it.id == productId }
        return if (product != null) {
            randomResult(
                product
            )
        } else {
            Result.failure(NoSuchElementException())
        }
    }

    private fun randomSignInResult(
        isCredentialsCorrect: Boolean,
        user: UserModel
    ): Result<UserModel> {
        if ((0..100).random() < 10) {
            return Result.failure(RuntimeException())
        } else {
            if (isCredentialsCorrect) return Result.success(user)
            return Result.failure(AuthenticatorException())
        }
    }

    override suspend fun signIn(login: String, password: String): Result<UserModel> {
        randomDelay()
        val isCredentialsCorrect = login == LOGIN && password == PASSWORD
        return randomSignInResult(isCredentialsCorrect, user)
    }

    override suspend fun signOut() {

    }

    override suspend fun getUser(): Result<UserModel> {
        randomDelay()
        return randomResult(user)
    }

    override fun getOrders(): Flow<PagingData<OrderModel>> {
//        randomDelay()
//        return randomResult(
//            StubData.ordersList
//        )
        return flow { }
    }

    override fun getActiveOrders(): Flow<PagingData<OrderModel>> {
//        randomDelay()
//        return randomResult(
//            StubData.ordersList.filter { it.status == "in_work" }
//        )
        return flow { }
    }

    override suspend fun cancelOrder(orderId: String): Result<OrderModel> {
        if ((0..100).random() < 10) {
            return Result.failure(RuntimeException())
        } else {
            val result = StubData.cancelOrder(orderId)
            if (result != null) return Result.success(result)
            return Result.failure(NoSuchElementException())
        }
    }

    private suspend fun randomDelay() {
        delay((100L..1000L).random())
    }

    private fun <T> randomResult(data: T): Result<T> =
        if ((0..100).random() < 5) {
            Result.failure(RuntimeException())
        } else {
            Result.success(data)
        }

    companion object {

        private const val LOGIN = "user1@mail.com"
        private const val PASSWORD = "12345678"

        private val user = UserModel(
            name = "Анна",
            surname = "Виноградова",
            occupation = "Разработчик",
            avatarUrl = "https://i.ibb.co/h7DVHqJ/Saitama.png"
        )
    }
}