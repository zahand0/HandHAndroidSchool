package com.zahand0.cowboys.domain.use_cases.get_active_orders

import androidx.paging.PagingData
import com.zahand0.cowboys.domain.model.OrderModel
import kotlinx.coroutines.flow.Flow

interface GetActiveOrdersUseCase {
    operator fun invoke(): Flow<PagingData<OrderModel>>
}