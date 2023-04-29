package com.zahand0.cowboys.presentation.ui.screen.orders

import com.zahand0.cowboys.domain.model.OrderModel

sealed class CancelOrderResult {
    object Failure : CancelOrderResult()
    data class Success(val order: OrderModel) : CancelOrderResult()
}
