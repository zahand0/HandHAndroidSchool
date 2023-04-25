package com.zahand0.cowboys.data.remote

import com.zahand0.cowboys.data.dto.SignInResponse
import com.zahand0.cowboys.data.dto.UserDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT

interface CowboysApi {

    @PUT("user/signin")
    suspend fun signIn(@Body credentials: Map<String, String>): SignInResponse

    @GET("user")
    suspend fun getUser(@Header("Authorization") token: String): UserDto
}