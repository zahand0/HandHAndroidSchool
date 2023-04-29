package com.zahand0.cowboys.domain.use_cases.get_all_products

import androidx.paging.PagingData
import com.zahand0.cowboys.domain.model.ProductModel
import kotlinx.coroutines.flow.Flow

interface GetAllProductsUseCase {
    operator fun invoke(): Flow<PagingData<ProductModel>>
}