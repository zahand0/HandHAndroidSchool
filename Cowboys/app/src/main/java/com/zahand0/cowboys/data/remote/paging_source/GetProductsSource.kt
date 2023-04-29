package com.zahand0.cowboys.data.remote.paging_source

import android.accounts.AuthenticatorException
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.zahand0.cowboys.data.remote.CowboysApi
import com.zahand0.cowboys.data.toProductModels
import com.zahand0.cowboys.domain.model.ProductModel

class GetProductsSource(
    private val cowboysApi: CowboysApi,
    private val token: String?
) : PagingSource<Int, ProductModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductModel> {
        if (token == null) {
            return LoadResult.Error(AuthenticatorException())
        }
        return try {
            val nextPageNumber = params.key ?: 0
            val response = cowboysApi.getProducts(
                token = token,
                fields = FIELDS,
                limit = LIMIT.toString(),
                offset = (LIMIT * nextPageNumber).toString()
            )
            LoadResult.Page(
                data = response.data.toProductModels(),
                prevKey = null,
                // HARDCODED BECAUSE SERVER SENDS ALL PRODUCTS REGARDLESS OF QUERY
                nextKey = null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, ProductModel>): Int? {
        return null
    }

    companion object {
        private const val TAG = "GetMessagesSource"
        private const val LIMIT = 20
        private const val FIELDS = "id,title,department,price,preview"
    }
}