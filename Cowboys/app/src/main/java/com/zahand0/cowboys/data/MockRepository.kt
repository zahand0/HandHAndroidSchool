package com.zahand0.cowboys.data

import com.zahand0.cowboys.domain.model.Product
import kotlinx.coroutines.delay
import java.util.UUID

class MockRepository {
    companion object {
        private val productIds = (0..5).map {
            UUID.randomUUID().toString()
        }
    }

    suspend fun getProducts(): Result<List<Product>> {
        randomDelay()
        return randomResult(
            listOf(
                Product(
                    id = productIds[0],
                    title = "PUMA ESS+ Tape Sweatpants TR cl",
                    category = "Брюки спортивные",
                    price = 4990,
                    previewUrl = "https://a.lmcdn.ru/product/R/T/RTLABD432202_19416359_1_v1_2x.jpg"
                ),
                Product(
                    id = productIds[1],
                    title = "O'stin",
                    category = "Брюки",
                    price = 1999,
                    previewUrl = "https://a.lmcdn.ru/img600x866/M/P/MP002XW0FG82_17574293_1_v1_2x.jpg"
                ),
                Product(
                    id = productIds[2],
                    title = "Barmariska",
                    category = "Кардиган",
                    price = 13999,
                    previewUrl = "https://a.lmcdn.ru/img600x866/M/P/MP002XW0KJN0_17886905_1_v1.jpeg"
                ),
                Product(
                    id = productIds[3],
                    title = "PlayToday",
                    category = "Куртка",
                    price = 4799,
                    previewUrl = "https://a.lmcdn.ru/img600x866/M/P/MP002XB01G7M_16658844_1_v1.jpg"
                ),
                Product(
                    id = productIds[4],
                    title = "Colin's",
                    category = "Юбка",
                    price = 1990,
                    previewUrl = "https://a.lmcdn.ru/img600x866/M/P/MP002XW15ULK_17398729_1_v1_2x.jpg"
                ),
                Product(
                    id = productIds[5],
                    title = "Befree",
                    category = "Куртка утепленная",
                    price = 3999,
                    previewUrl = "https://a.lmcdn.ru/img600x866/M/P/MP002XW0MF7Y_18771831_1_v1_2x.jpg"
                ),
            )
        )
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
}