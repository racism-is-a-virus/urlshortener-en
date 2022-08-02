package com.takehomeevaluation.urlshortener.data

import com.takehomeevaluation.urlshortener.model.OriginalUrl
import com.takehomeevaluation.urlshortener.model.ShortenedUrl

interface UrlShortenerRepository {
    suspend fun registerUrl(originalUrl: OriginalUrl): ShortenedUrl
}