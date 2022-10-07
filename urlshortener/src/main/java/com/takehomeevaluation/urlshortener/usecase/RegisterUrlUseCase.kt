package com.takehomeevaluation.urlshortener.usecase

import com.takehomeevaluation.core.interfaces.UseCase
import com.takehomeevaluation.urlshortener.data.UrlShortenerRepository
import com.takehomeevaluation.urlshortener.model.OriginalUrl
import com.takehomeevaluation.urlshortener.model.ShortenedUrl

class RegisterUrlUseCase(private val repo: UrlShortenerRepository) : UseCase<OriginalUrl, ShortenedUrl> {
    override suspend fun execute(param: OriginalUrl) = repo.registerUrl(param)
}