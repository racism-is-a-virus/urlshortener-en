package com.takehomeevaluation.urlshortener.data.remote

import com.google.gson.GsonBuilder
import com.takehomeevaluation.core.exceptions.RepositoryException
import com.takehomeevaluation.core.exceptions.network.InfoNotFoundErrorException
import com.takehomeevaluation.core.exceptions.network.ServerErrorException
import com.takehomeevaluation.core.extensions.fromJson
import com.takehomeevaluation.core.network.ApiClientBuilder
import com.takehomeevaluation.urlshortener.data.remote.model.ShortenedUrlResponse
import com.takehomeevaluation.urlshortener.model.OriginalUrl
import com.takehomeevaluation.utils.JsonStringFiles
import com.takehomeevaluation.utils.enqueueResponse
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test

class UrlShortenerRemoteRepositoryTest {

    private val mockWebServer = MockWebServer()

    private lateinit var urlShortenerService: UrlShortenerService

    private lateinit var urlShortenerRemoteRepository: UrlShortenerRemoteRepository

    @Before
    fun setup() {
        mockWebServer.start()
        val url = mockWebServer.url("").toString()
        urlShortenerService = ApiClientBuilder.createServiceApi(UrlShortenerService::class.java, url)
        urlShortenerRemoteRepository = UrlShortenerRemoteRepository(urlShortenerService)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `GIVEN UrlShortenerRemoteRepository WHEN registerUrl response 201 THEN expectedResponse success`() =
        runBlocking {
            // GIVEN
            val originalUrl = mockk<OriginalUrl>(relaxed = true)
            val jsonResponse = JsonStringFiles.jsonUrlShortenerResponse

            mockWebServer.enqueueResponse(201, jsonResponse)

            // WHEN
            val response = urlShortenerRemoteRepository.registerUrl(originalUrl)

            // THEN
            val expectedResponse = GsonBuilder().create().fromJson<ShortenedUrlResponse>(jsonResponse).toModel()
            assertEquals(expectedResponse, response)
        }

    @Test(expected = ServerErrorException::class)
    fun `GIVEN api error 500 WHEN UrlShortenerRemoteRepository registerUrl THEN ServerErrorException response expected`() =
        runBlocking {
            val originalUrl = mockk<OriginalUrl>(relaxed = true)
            mockWebServer.enqueueResponse(500, "")
            urlShortenerRemoteRepository.registerUrl(originalUrl)
            fail()
        }

    @Test(expected = InfoNotFoundErrorException::class)
    fun `GIVEN api error 404 WHEN UrlShortenerRemoteRepository registerUrl THEN InfoNotFoundErrorException response expected`() =
        runBlocking {
            val originalUrl = mockk<OriginalUrl>(relaxed = true)
            mockWebServer.enqueueResponse(404, "")
            urlShortenerRemoteRepository.registerUrl(originalUrl)
            fail()
        }

    @Test(expected = RepositoryException::class)
    fun `GIVEN api error 401 WHEN UrlShortenerRemoteRepository registerUrl THEN RepositoryException response expected`() =
        runBlocking {
            val originalUrl = mockk<OriginalUrl>(relaxed = true)
            mockWebServer.enqueueResponse(401, "")
            urlShortenerRemoteRepository.registerUrl(originalUrl)
            fail()
        }
}
