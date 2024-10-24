package com.midhun.composeapp.Network

sealed class NetworkResultState<out T> {
    data class Loading (val boolean: Boolean?): NetworkResultState<Nothing>()
    data class Success<out T>(val data: T) : NetworkResultState<T>()
    data class Error(val errorCode: Int, val errorMessage: String?) : NetworkResultState<Nothing>()
    data class Exception<T : Any>(val e: Throwable) : NetworkResultState<T>()
}