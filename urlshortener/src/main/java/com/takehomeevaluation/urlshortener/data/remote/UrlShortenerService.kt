package com.takehomeevaluation.urlshortener.data.remote

import com.takehomeevaluation.urlshortener.data.remote.model.OriginalUrlInput
import com.takehomeevaluation.urlshortener.data.remote.model.ShortenedUrlResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UrlShortenerService {
    @POST("/api/alias")
    suspend fun registerUrl(@Body originalUrl: OriginalUrlInput): Response<ShortenedUrlResponse>
}