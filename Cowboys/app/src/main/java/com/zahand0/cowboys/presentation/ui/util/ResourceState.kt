package com.zahand0.cowboys.presentation.ui.util

sealed interface ResourceState<out T> {
    object Loading : ResourceState<Nothing>
    class Success<T>(val data: T) : ResourceState<T>
    class Error(e: Throwable) : ResourceState<Nothing>
}