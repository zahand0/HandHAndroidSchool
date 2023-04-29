package com.zahand0.cowboys.data.remote

import com.zahand0.cowboys.data.dto.OrderDto
import com.zahand0.cowboys.data.dto.OrdersDto
import com.zahand0.cowboys.data.dto.ProductDto
import com.zahand0.cowboys.data.dto.ProductsDto
import com.zahand0.cowboys.data.dto.ProfileDto
import com.zahand0.cowboys.data.dto.SignInResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface CowboysApi {

    @PUT("user/signin")
    suspend fun signIn(@Body credentials: Map<String, String>): SignInResponse

    @GET("user")
    suspend fun getUser(@Header("Authorization") token: String): ProfileDto

    @GET("products")
    suspend fun getProducts(
        @Header("Authorization") token: String,
        @Query("fields") fields: String,
        @Query("limit") limit: String,
        @Query("offset") offset: String,
    ): ProductsDto

    @GET("products/{id}")
    suspend fun getProduct(
        @Header("Authorization") token: String,
        @Path("id") id: String,
    ): ProductDto

    @GET("orders")
    suspend fun getOrders(
        @Header("Authorization") token: String,
        @Query("limit") limit: String,
        @Query("offset") offset: String,
    ): OrdersDto

    @PUT("orders/{id}")
    suspend fun cancelOrder(
        @Header("Authorization") token: String,
        @Path("id") orderId: String,
    ): OrderDto
}