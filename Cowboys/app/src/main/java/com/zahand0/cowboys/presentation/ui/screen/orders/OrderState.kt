package com.zahand0.cowboys.presentation.ui.screen.orders

import com.zahand0.cowboys.domain.model.OrderModel

data class OrderState(
    val order: OrderModel,
    var isLoading: Boolean
)

fun OrderModel.toOrderState(isLoading: Boolean = false): OrderState =
    OrderState(this, isLoading)