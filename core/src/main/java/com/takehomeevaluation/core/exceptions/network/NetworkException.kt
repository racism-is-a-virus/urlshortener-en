package com.takehomeevaluation.core.exceptions.network

open class NetworkException(message: String? = null, cause: Throwable? = null) :
    Exception(message ?: cause?.message, cause)