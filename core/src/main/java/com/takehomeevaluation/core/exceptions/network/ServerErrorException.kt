package com.takehomeevaluation.core.exceptions.network

class ServerErrorException(message: String? = null, cause: Throwable? = null) : NetworkException(message, cause)