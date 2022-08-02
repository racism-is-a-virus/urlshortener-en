package com.takehomeevaluation.core.exceptions

open class RepositoryException(message: String? = null, cause: Throwable? = null) :
    Exception(message ?: cause?.message, cause)