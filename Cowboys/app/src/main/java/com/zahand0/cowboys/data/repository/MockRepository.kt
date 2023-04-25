package com.zahand0.cowboys.data.repository

import android.accounts.AuthenticatorException
import com.zahand0.cowboys.data.StubData
import com.zahand0.cowboys.domain.model.Order
import com.zahand0.cowboys.domain.model.Product
import com.zahand0.cowboys.domain.model.ProductDetails
import com.zahand0.cowboys.domain.model.User
import com.zahand0.cowboys.domain.repository.Repository
import kotlinx.coroutines.delay
import javax.inject.Inject

class MockRepository @Inject constructor() : Repository {

    override suspend fun getProducts(): Result<List<Product>> {
        randomDelay()
        return randomResult(
            StubData.products
        )
    }

    override suspend fun getProductDetails(productId: String): Result<ProductDetails> {
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

    private fun randomSignInResult(isCredentialsCorrect: Boolean, user: User): Result<User> {
        if ((0..100).random() < 10) {
            return Result.failure(RuntimeException())
        } else {
            if (isCredentialsCorrect) return Result.success(user)
            return Result.failure(AuthenticatorException())
        }
    }

    override suspend fun signIn(login: String, password: String): Result<User> {
        randomDelay()
        val isCredentialsCorrect = login == LOGIN && password == PASSWORD
        return randomSignInResult(isCredentialsCorrect, user)
    }

    override suspend fun signOut() {

    }

    override suspend fun getUser(): Result<User> {
        randomDelay()
        return randomResult(user)
    }

    override suspend fun getOrders(): Result<List<Order>> {
        randomDelay()
        return randomResult(
            StubData.ordersList
        )
    }

    override suspend fun getActiveOrders(): Result<List<Order>> {
        randomDelay()
        return randomResult(
            StubData.ordersList.filter { it.status == "in_work" }
        )
    }

    override suspend fun cancelOrder(orderId: String): Result<Order> {
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

        private val user = User(
            name = "Анна",
            surname = "Виноградова",
            occupation = "Разработчик",
            avatarUrl = "https://i.ibb.co/h7DVHqJ/Saitama.png"
        )
    }
}