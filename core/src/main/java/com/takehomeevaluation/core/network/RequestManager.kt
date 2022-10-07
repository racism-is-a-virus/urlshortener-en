package com.takehomeevaluation.core.network

import com.takehomeevaluation.core.exceptions.RepositoryException
import com.takehomeevaluation.core.exceptions.network.InfoNotFoundErrorException
import com.takehomeevaluation.core.exceptions.network.NetworkException
import com.takehomeevaluation.core.exceptions.network.ServerErrorException
import java.io.IOException
import retrofit2.Response
import timber.log.Timber

object RequestManager {

    @Throws(
        InfoNotFoundErrorException::class,
        ServerErrorException::class,
        NetworkException::class,
        RepositoryException::class
    )
    suspend fun <T> requestFromApi(request: (suspend () -> Response<T>)): T? {
        try {
            val response = request()
            if (response.isSuccessful) {
                // attention point: here it may happen to have some crash related to the json conversion
                return response.body()
            } else {
                val message = response.message()
                throw when (response.code()) {
                    404 -> InfoNotFoundErrorException(message = message)
                    500 -> ServerErrorException(message = message)
                    else -> RepositoryException(message = message)
                }
            }
        } catch (exception: Exception) {
            Timber.e(exception, "Request error: ${exception.message}")
            when (exception) {
                is IOException -> throw NetworkException(cause = exception)
                else -> throw exception
            }
        }
    }
}