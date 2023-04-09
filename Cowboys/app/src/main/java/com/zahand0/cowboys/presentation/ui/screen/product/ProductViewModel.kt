package com.zahand0.cowboys.presentation.ui.screen.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zahand0.cowboys.domain.model.ProductDetails
import com.zahand0.cowboys.domain.use_cases.get_product_details.GetProductDetailsUseCase
import com.zahand0.cowboys.presentation.ui.util.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getProductDetailsUseCase: GetProductDetailsUseCase
) : ViewModel() {

    private val _product: MutableStateFlow<ResourceState<ProductDetails>> =
        MutableStateFlow(ResourceState.Loading)
    val product: StateFlow<ResourceState<ProductDetails>> = _product

    private val _selectedImageItemIndex = MutableStateFlow(0)
    val selectedImageItemIndex: StateFlow<Int> get() = _selectedImageItemIndex

    private val _selectedSize = MutableStateFlow(0)
    val selectedSize: StateFlow<Int> get() = _selectedSize

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

    fun selectImageItem(index: Int) {
        _selectedImageItemIndex.value = index
    }

    fun selectSize(index: Int) {
        _selectedSize.value = index
    }
}