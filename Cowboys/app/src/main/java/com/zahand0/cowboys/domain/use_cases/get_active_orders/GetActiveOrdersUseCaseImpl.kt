package com.zahand0.cowboys.domain.use_cases.get_active_orders

import androidx.paging.PagingData
import com.zahand0.cowboys.domain.model.OrderModel
import com.zahand0.cowboys.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetActiveOrdersUseCaseImpl @Inject constructor(
    private val repository: Repository
) : GetActiveOrdersUseCase {
    override fun invoke(): Flow<PagingData<OrderModel>> =
        repository.getActiveOrders()
}