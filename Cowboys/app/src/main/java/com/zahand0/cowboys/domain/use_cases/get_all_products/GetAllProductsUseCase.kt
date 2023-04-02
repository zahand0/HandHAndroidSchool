package com.zahand0.cowboys.domain.use_cases.get_all_products

import com.zahand0.cowboys.domain.model.Product

interface GetAllProductsUseCase {
    suspend operator fun invoke(): Result<List<Product>>
}