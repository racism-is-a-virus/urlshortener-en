package com.takehomeevaluation.urlshortener.data.remote

import com.takehomeevaluation.core.exceptions.RepositoryException
import com.takehomeevaluation.core.network.RequestManager
import com.takehomeevaluation.urlshortener.data.UrlShortenerRepository
import com.takehomeevaluation.urlshortener.extensions.toInput
import com.takehomeevaluation.urlshortener.model.OriginalUrl
import com.takehomeevaluation.urlshortener.model.ShortenedUrl

class UrlShortenerRemoteRepository(private val urlShortenerService: UrlShortenerService) :
    UrlShortenerRepository {
    override suspend fun registerUrl(originalUrl: OriginalUrl): ShortenedUrl {
        return RequestManager.requestFromApi { urlShortenerService.registerUrl(originalUrl.toInput()) }?.toModel()
            ?: throw RepositoryException()
    }
}