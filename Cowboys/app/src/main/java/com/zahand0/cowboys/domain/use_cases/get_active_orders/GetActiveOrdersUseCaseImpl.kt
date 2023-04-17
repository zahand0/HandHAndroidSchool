package com.zahand0.cowboys.domain.use_cases.get_active_orders

import com.zahand0.cowboys.domain.model.Order
import com.zahand0.cowboys.domain.repository.Repository
import javax.inject.Inject

class GetActiveOrdersUseCaseImpl @Inject constructor(
    private val repository: Repository
) : GetActiveOrdersUseCase {
    override suspend fun invoke(): Result<List<Order>> =
        repository.getActiveOrders()
}