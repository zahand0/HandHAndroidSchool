package com.zahand0.cowboys.domain.use_cases.get_all_products

import com.zahand0.cowboys.domain.model.Product
import com.zahand0.cowboys.domain.repository.Repository
import javax.inject.Inject

class GetAllProductsUseCaseImpl @Inject constructor(
    private val repository: Repository
) : GetAllProductsUseCase {
    override suspend fun invoke(): Result<List<Product>> = repository.getProducts()
}