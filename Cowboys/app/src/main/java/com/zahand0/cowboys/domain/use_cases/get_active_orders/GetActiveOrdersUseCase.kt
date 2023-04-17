package com.zahand0.cowboys.domain.use_cases.get_active_orders

import com.zahand0.cowboys.domain.model.Order

interface GetActiveOrdersUseCase {
    suspend operator fun invoke(): Result<List<Order>>
}