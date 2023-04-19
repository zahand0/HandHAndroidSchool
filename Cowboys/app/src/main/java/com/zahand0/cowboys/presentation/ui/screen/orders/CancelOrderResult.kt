package com.zahand0.cowboys.presentation.ui.screen.orders

import com.zahand0.cowboys.domain.model.Order

sealed class CancelOrderResult {
    object Failure : CancelOrderResult()
    data class Success(val order: Order) : CancelOrderResult()
}
