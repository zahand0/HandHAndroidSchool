package com.zahand0.cowboys.domain.use_cases.get_product_details

import com.zahand0.cowboys.domain.model.ProductDetails

interface GetProductDetailsUseCase {
    suspend operator fun invoke(productId: String): Result<ProductDetails>
}