package com.s097t0r1.lycoris.data


sealed class Result<out R>
data class Success<out T>(val data: T) : Result<T>()
data class Error(val e: Exception) : Result<Nothing>()