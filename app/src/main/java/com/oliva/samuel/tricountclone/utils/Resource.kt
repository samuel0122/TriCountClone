package com.oliva.samuel.tricountclone.utils

sealed interface Resource<out T> {
    data object Loading : Resource<Nothing>
    data class Error<T>(val throwable: Throwable, val message: String?) : Resource<T>
    data class Success<T>(val data: T) : Resource<T>
}
