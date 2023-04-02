package com.zahand0.cowboys.presentation.ui.screen.catalog

import androidx.lifecycle.ViewModel
import com.zahand0.cowboys.domain.model.Product
import com.zahand0.cowboys.domain.use_cases.get_all_products.GetAllProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.invoke
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val getAllProductsUseCase: GetAllProductsUseCase
) : ViewModel() {

    private val _products: MutableStateFlow<Result<List<Product>>> =
        MutableStateFlow(Result.success(listOf()))
    val products: StateFlow<Result<List<Product>>> = _products

    suspend fun refreshProducts() {
        Dispatchers.IO {
            _products.value = getAllProductsUseCase()
        }
    }
}