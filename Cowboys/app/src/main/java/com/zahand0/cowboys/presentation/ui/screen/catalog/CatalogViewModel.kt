package com.zahand0.cowboys.presentation.ui.screen.catalog

import androidx.lifecycle.ViewModel
import com.zahand0.cowboys.data.MockRepository
import com.zahand0.cowboys.domain.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.invoke

class CatalogViewModel : ViewModel() {

    private val repository = MockRepository()

    private val _products: MutableStateFlow<Result<List<Product>>> =
        MutableStateFlow(Result.success(listOf()))
    val products: StateFlow<Result<List<Product>>> = _products

    suspend fun refreshProducts() {
        Dispatchers.IO {
            _products.value = repository.getProducts()
        }
    }
}