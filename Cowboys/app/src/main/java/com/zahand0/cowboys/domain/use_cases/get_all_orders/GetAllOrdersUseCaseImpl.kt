package com.zahand0.cowboys.domain.use_cases.get_all_orders

import androidx.paging.PagingData
import com.zahand0.cowboys.domain.model.OrderModel
import com.zahand0.cowboys.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllOrdersUseCaseImpl @Inject constructor(
    private val repository: Repository
) : GetAllOrdersUseCase {
    override fun invoke(): Flow<PagingData<OrderModel>> =
        repository.getOrders()
}