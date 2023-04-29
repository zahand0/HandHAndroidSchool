package com.zahand0.cowboys.presentation.ui.screen.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.zahand0.cowboys.domain.use_cases.cancel_order.CancelOrderUseCase
import com.zahand0.cowboys.domain.use_cases.get_active_orders.GetActiveOrdersUseCase
import com.zahand0.cowboys.domain.use_cases.get_all_orders.GetAllOrdersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val getAllOrdersUseCase: GetAllOrdersUseCase,
    private val getActiveOrdersUseCase: GetActiveOrdersUseCase,
    private val cancelOrderUseCase: CancelOrderUseCase
) : ViewModel() {

    val allOrders = getAllOrdersUseCase().cachedIn(viewModelScope)

    val activeOrders = getActiveOrdersUseCase().cachedIn(viewModelScope)

    private val _cancelOrderResultFlow = MutableSharedFlow<CancelOrderResult>()
    val cancelOrderResultFlow: SharedFlow<CancelOrderResult> get() = _cancelOrderResultFlow

    fun cancelOrder(orderId: String) {
        viewModelScope.launch {
//            setLoadingOrder(orderId)
            val result = cancelOrderUseCase(orderId)
            result.onFailure {
                _cancelOrderResultFlow.emit(CancelOrderResult.Failure)
//                removeLoadingStatus(orderId)
            }
            result.onSuccess {
                _cancelOrderResultFlow.emit(CancelOrderResult.Success(it))
            }
        }
    }

//    private fun setLoadingOrder(orderId: String) {
//        val orders = _allOrders.value
//        if (orders is ResourceState.Success) {
//            val newOrders = orders.data.map {
//                if (it.order.id == orderId) {
//                    it.copy(isLoading = true)
//                } else {
//                    it
//                }
//            }
//            _allOrders.value = ResourceState.Success(newOrders)
//        }
//    }
//
//    private fun removeLoadingStatus(orderId: String, setCancelled: Boolean = false) {
//        val orders = _allOrders.value
//        if (orders is ResourceState.Success) {
//            val newOrders = orders.data.map {
//                if (it.order.id == orderId) {
//                    if (setCancelled) {
//                        OrderState(it.order.copy(status = "cancelled"), false)
//                    } else {
//                        it.copy(isLoading = false)
//                    }
//                } else {
//                    it
//                }
//            }
//            _allOrders.value = ResourceState.Success(newOrders)
//        }
//    }
}