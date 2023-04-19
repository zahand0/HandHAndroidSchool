package com.zahand0.cowboys.domain.use_cases.cancel_order

import com.zahand0.cowboys.domain.model.Order

interface CancelOrderUseCase {

    suspend operator fun invoke(orderId: String): Result<Order>
}