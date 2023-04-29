package com.zahand0.cowboys.data

import android.graphics.Color
import com.zahand0.cowboys.data.dto.Badge
import com.zahand0.cowboys.data.dto.Order
import com.zahand0.cowboys.data.dto.Product
import com.zahand0.cowboys.data.dto.ProductDetails
import com.zahand0.cowboys.data.dto.ProductSize
import com.zahand0.cowboys.data.dto.Profile
import com.zahand0.cowboys.domain.model.BadgeModel
import com.zahand0.cowboys.domain.model.OrderModel
import com.zahand0.cowboys.domain.model.ProductDetailsModel
import com.zahand0.cowboys.domain.model.ProductModel
import com.zahand0.cowboys.domain.model.ProductSizeModel
import com.zahand0.cowboys.domain.model.UserModel

fun Profile.toUserModel() =
    UserModel(
        name = name,
        surname = surname,
        occupation = occupation,
        avatarUrl = "https://i.ibb.co/h7DVHqJ/Saitama.png"
    )

fun Product.toProductModel(): ProductModel =
    ProductModel(
        id = id,
        title = title,
        department = department,
        price = price,
        preview = preview
    )

fun List<Product>.toProductModels(): List<ProductModel> =
    map { it.toProductModel() }

fun ProductDetails.toProductDetailsModel(): ProductDetailsModel =
    ProductDetailsModel(
        id = id,
        title = title,
        department = department,
        price = price,
        preview = preview,
        badge = badge[0].toBadgeModel(),
        description = description,
        images = images,
        sizes = sizes.toProductSizeModels(),
        details = details
    )

fun ProductDetails.toProductModel(): ProductModel =
    ProductModel(
        id = id,
        title = title,
        department = department,
        price = price,
        preview = preview
    )

fun Badge.toBadgeModel(): BadgeModel =
    BadgeModel(
        value = value,
        color = Color.parseColor(color)
    )

fun ProductSize.toProductSizeModel(): ProductSizeModel =
    ProductSizeModel(
        value = value,
        isAvailable = isAvailable
    )

fun List<ProductSize>.toProductSizeModels(): List<ProductSizeModel> =
    map { it.toProductSizeModel() }

fun Order.toOrderModel(productModel: ProductModel): OrderModel =
    OrderModel(
        id = id,
        number = number,
        productSize = productSize,
        productQuantity = productQuantity,
        product = productModel,
        productPreview = productPreview,
        createdAt = createdAt,
        etd = etd,
        deliveryAddress = deliveryAddress,
        status = status
    )

suspend fun List<Order>.toOrderModels(getProduct: suspend (id: String) -> ProductModel): List<OrderModel> =
    map { it.toOrderModel(getProduct(it.productId)) }