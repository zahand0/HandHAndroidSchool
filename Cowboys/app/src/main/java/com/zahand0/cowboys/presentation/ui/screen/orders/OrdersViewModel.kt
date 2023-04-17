package com.zahand0.cowboys.presentation.ui.screen.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zahand0.cowboys.domain.model.Order
import com.zahand0.cowboys.domain.use_cases.get_active_orders.GetActiveOrdersUseCase
import com.zahand0.cowboys.domain.use_cases.get_all_orders.GetAllOrdersUseCase
import com.zahand0.cowboys.presentation.ui.util.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val getAllOrdersUseCase: GetAllOrdersUseCase,
    private val getActiveOrdersUseCase: GetActiveOrdersUseCase
) : ViewModel() {

    private val _allOrders: MutableStateFlow<ResourceState<List<Order>>> =
        MutableStateFlow(ResourceState.Loading)
    val allOrders: StateFlow<ResourceState<List<Order>>> get() = _allOrders

    private val _activeOrders: MutableStateFlow<ResourceState<List<Order>>> =
        MutableStateFlow(ResourceState.Loading)
    val activeOrders: StateFlow<ResourceState<List<Order>>> get() = _activeOrders

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
            result.onSuccess {
                _allOrders.value = ResourceState.Success(it)
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
            result.onSuccess {
                _activeOrders.value = ResourceState.Success(it)
            }
        }
    }
}