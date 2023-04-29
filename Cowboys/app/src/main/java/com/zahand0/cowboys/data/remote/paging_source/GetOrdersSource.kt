package com.zahand0.cowboys.data.remote.paging_source

import android.accounts.AuthenticatorException
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.zahand0.cowboys.data.remote.CowboysApi
import com.zahand0.cowboys.data.toOrderModels
import com.zahand0.cowboys.data.toProductModel
import com.zahand0.cowboys.domain.model.OrderModel

class GetOrdersSource(
    private val cowboysApi: CowboysApi,
    private val token: String?,
    private val onlyActiveOrders: Boolean = false
) : PagingSource<Int, OrderModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, OrderModel> {
        if (token == null) {
            return LoadResult.Error(AuthenticatorException())
        }
        return try {
            val nextPageNumber = params.key ?: 0
            val response = cowboysApi.getOrders(
                token = token,
                limit = LIMIT.toString(),
                offset = (LIMIT * nextPageNumber).toString()
            )
            val data = response.data
                .filter {
                    if (onlyActiveOrders)
                        it.status == STATUS_IN_WORK
                    else
                        true
                }
                .toOrderModels {
                    cowboysApi.getProduct(token, it).data.toProductModel()
                }
            LoadResult.Page(
                data = data,
                prevKey = null,
                // HARDCODED BECAUSE SERVER SENDS ALL ORDERS REGARDLESS OF QUERY
                nextKey = null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, OrderModel>): Int? = null

    companion object {
        private const val TAG = "GetOrdersSource"
        private const val STATUS_IN_WORK = "in_work"
        private const val LIMIT = 20
    }
}