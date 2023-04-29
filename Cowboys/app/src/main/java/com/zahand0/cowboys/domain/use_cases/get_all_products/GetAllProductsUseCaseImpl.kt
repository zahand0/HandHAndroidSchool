package com.zahand0.cowboys.domain.use_cases.get_all_products

import androidx.paging.PagingData
import com.zahand0.cowboys.domain.model.ProductModel
import com.zahand0.cowboys.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllProductsUseCaseImpl @Inject constructor(
    private val repository: Repository
) : GetAllProductsUseCase {
    override fun invoke(): Flow<PagingData<ProductModel>> = repository.getProducts()
}