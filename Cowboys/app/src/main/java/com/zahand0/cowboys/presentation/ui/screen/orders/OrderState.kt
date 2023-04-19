package com.zahand0.cowboys.presentation.ui.screen.orders

import com.zahand0.cowboys.domain.model.Order

data class OrderState(
    val order: Order,
    val isLoading: Boolean
)

fun Order.toOrderState(isLoading: Boolean = false): OrderState =
    OrderState(this, isLoading)