package com.takehomeevaluation.urlshortener.usecase

import com.takehomeevaluation.core.exceptions.network.NetworkException
import com.takehomeevaluation.urlshortener.data.remote.UrlShortenerRemoteRepository
import com.takehomeevaluation.urlshortener.model.OriginalUrl
import com.takehomeevaluation.urlshortener.model.ShortenedUrl
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test

class RegisterUrlUseCaseTest {

    @MockK
    private lateinit var urlShortenerRemoteRepository: UrlShortenerRemoteRepository

    private lateinit var registerUrlUseCase: RegisterUrlUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        registerUrlUseCase = RegisterUrlUseCase(repo = urlShortenerRemoteRepository)
    }

    @Test
    fun `GIVEN originalUrl WHEN registerUrlUseCase execute THEN returns shortenedUrl response`() = runBlocking {
        // GIVEN
        val originalUrl = mockk<OriginalUrl>(relaxed = true)
        val shortenedUrl = mockk<ShortenedUrl>(relaxed = true)
        coEvery { urlShortenerRemoteRepository.registerUrl(originalUrl = any()) } returns shortenedUrl

        // WHEN
        val response = registerUrlUseCase.execute(param = originalUrl)

        // THEN
        assertEquals(shortenedUrl, response)
    }

    @Test(expected = NetworkException::class)
    fun `GIVEN NetworkException WHEN registerUrlUseCase execute THEN returns fails`() = runBlocking {
        // GIVEN
        coEvery { urlShortenerRemoteRepository.registerUrl(originalUrl = any()) } throws NetworkException()

        // WHEN
        registerUrlUseCase.execute(param = mockk())

        // THEN
        fail()
    }
}