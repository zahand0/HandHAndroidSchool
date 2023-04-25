package com.zahand0.cowboys.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreOperations {

    suspend fun saveAccessToken(token: String)

    fun readAccessToken(): Flow<String?>
}