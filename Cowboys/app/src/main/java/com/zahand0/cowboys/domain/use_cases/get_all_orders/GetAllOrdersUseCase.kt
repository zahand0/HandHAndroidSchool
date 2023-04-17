package com.zahand0.cowboys.domain.use_cases.get_all_orders

import com.zahand0.cowboys.domain.model.Order

interface GetAllOrdersUseCase {
    suspend operator fun invoke(): Result<List<Order>>
}