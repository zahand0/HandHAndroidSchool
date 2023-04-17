package com.zahand0.cowboys.domain.use_cases.get_all_orders

import com.zahand0.cowboys.domain.model.Order
import com.zahand0.cowboys.domain.repository.Repository
import javax.inject.Inject

class GetAllOrdersUseCaseImpl @Inject constructor(
    private val repository: Repository
) : GetAllOrdersUseCase {
    override suspend fun invoke(): Result<List<Order>> =
        repository.getOrders()
}