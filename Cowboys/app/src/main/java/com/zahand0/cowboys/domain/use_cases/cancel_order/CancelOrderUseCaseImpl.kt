package com.zahand0.cowboys.domain.use_cases.cancel_order

import com.zahand0.cowboys.domain.model.OrderModel
import com.zahand0.cowboys.domain.repository.Repository
import javax.inject.Inject

class CancelOrderUseCaseImpl @Inject constructor(
    private val repository: Repository
) : CancelOrderUseCase {
    override suspend fun invoke(orderId: String): Result<OrderModel> =
        repository.cancelOrder(orderId)
}