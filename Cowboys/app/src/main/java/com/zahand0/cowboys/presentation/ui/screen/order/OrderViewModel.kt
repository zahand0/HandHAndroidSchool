package com.zahand0.cowboys.presentation.ui.screen.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zahand0.cowboys.domain.model.ProductDetailsModel
import com.zahand0.cowboys.domain.use_cases.get_product_details.GetProductDetailsUseCase
import com.zahand0.cowboys.presentation.ui.util.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val getProductDetailsUseCase: GetProductDetailsUseCase
) : ViewModel() {

    private val _product: MutableStateFlow<ResourceState<ProductDetailsModel>> =
        MutableStateFlow(ResourceState.Loading)
    val product: StateFlow<ResourceState<ProductDetailsModel>> = _product

    fun refreshProduct(productId: String) {
        _product.value = ResourceState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val result = getProductDetailsUseCase(productId)
            result.onFailure {
                _product.value = ResourceState.Error(it)
            }
            result.onSuccess {
                _product.value = ResourceState.Success(it)
            }
        }
    }
}