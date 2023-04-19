package com.zahand0.cowboys.presentation.ui.screen.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zahand0.cowboys.domain.use_cases.cancel_order.CancelOrderUseCase
import com.zahand0.cowboys.domain.use_cases.get_active_orders.GetActiveOrdersUseCase
import com.zahand0.cowboys.domain.use_cases.get_all_orders.GetAllOrdersUseCase
import com.zahand0.cowboys.presentation.ui.util.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val getAllOrdersUseCase: GetAllOrdersUseCase,
    private val getActiveOrdersUseCase: GetActiveOrdersUseCase,
    private val cancelOrderUseCase: CancelOrderUseCase
) : ViewModel() {

    private val _allOrders: MutableStateFlow<ResourceState<List<OrderState>>> =
        MutableStateFlow(ResourceState.Loading)
    val allOrders: StateFlow<ResourceState<List<OrderState>>> get() = _allOrders

    private val _activeOrders: MutableStateFlow<ResourceState<List<OrderState>>> =
        MutableStateFlow(ResourceState.Loading)
    val activeOrders: StateFlow<ResourceState<List<OrderState>>> get() = _activeOrders

    private val _cancelOrderResultFlow = MutableSharedFlow<CancelOrderResult>()
    val cancelOrderResultFlow: SharedFlow<CancelOrderResult> get() = _cancelOrderResultFlow

    init {
        refreshAllOrders()
        refreshActiveOrders()
    }

    fun refreshAllOrders() {
        _allOrders.value = ResourceState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val result = getAllOrdersUseCase()
            result.onFailure {
                _allOrders.value = ResourceState.Error(it)
            }
            result.onSuccess { orders ->
                _allOrders.value = ResourceState.Success(orders.map { it.toOrderState() })
            }
        }
    }

    fun refreshActiveOrders() {
        _activeOrders.value = ResourceState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val result = getActiveOrdersUseCase()
            result.onFailure {
                _activeOrders.value = ResourceState.Error(it)
            }
            result.onSuccess { orders ->
                _activeOrders.value = ResourceState.Success(orders.map { it.toOrderState() })
            }
        }
    }

    fun cancelOrder(orderId: String) {
        viewModelScope.launch {
            setLoadingOrder(orderId)
            val result = cancelOrderUseCase(orderId)
            result.onFailure {
                _cancelOrderResultFlow.emit(CancelOrderResult.Failure)
                removeLoadingStatus(orderId)
            }
            result.onSuccess {
                _cancelOrderResultFlow.emit(CancelOrderResult.Success(it))
                refreshAllOrders()
                refreshActiveOrders()
            }
        }
    }

    private fun setLoadingOrder(orderId: String) {
        val orders = _allOrders.value
        if (orders is ResourceState.Success) {
            val newOrders = orders.data.map {
                if (it.order.id == orderId) {
                    it.copy(isLoading = true)
                } else {
                    it
                }
            }
            _allOrders.value = ResourceState.Success(newOrders)
        }
    }

    private fun removeLoadingStatus(orderId: String, setCancelled: Boolean = false) {
        val orders = _allOrders.value
        if (orders is ResourceState.Success) {
            val newOrders = orders.data.map {
                if (it.order.id == orderId) {
                    if (setCancelled) {
                        OrderState(it.order.copy(status = "cancelled"), false)
                    } else {
                        it.copy(isLoading = false)
                    }
                } else {
                    it
                }
            }
            _allOrders.value = ResourceState.Success(newOrders)
        }
    }
}