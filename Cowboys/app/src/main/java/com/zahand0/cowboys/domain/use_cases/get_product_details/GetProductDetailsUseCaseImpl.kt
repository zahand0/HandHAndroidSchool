package com.zahand0.cowboys.domain.use_cases.get_product_details

import com.zahand0.cowboys.domain.model.ProductDetailsModel
import com.zahand0.cowboys.domain.repository.Repository
import javax.inject.Inject

class GetProductDetailsUseCaseImpl @Inject constructor(
    private val repository: Repository
) : GetProductDetailsUseCase {
    override suspend fun invoke(productId: String): Result<ProductDetailsModel> =
        repository.getProductDetails(productId)
}