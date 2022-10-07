package com.takehomeevaluation.urlshortener.ui.shortenerlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.takehomeevaluation.core.ViewState
import com.takehomeevaluation.core.exceptions.RepositoryException
import com.takehomeevaluation.core.exceptions.network.NetworkException
import com.takehomeevaluation.core.exceptions.network.ServerErrorException
import com.takehomeevaluation.urlshortener.model.OriginalUrl
import com.takehomeevaluation.urlshortener.model.ShortenedUrl
import com.takehomeevaluation.urlshortener.model.UrlLinks
import com.takehomeevaluation.urlshortener.usecase.RegisterUrlUseCase
import com.takehomeevaluation.utils.CoroutineTestRule
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifySequence
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class UrlShortenerListViewModelTest {

    @Rule
    @JvmField
    val coroutineTestRule = CoroutineTestRule()

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val registerUrlUseCase: RegisterUrlUseCase = mockk(relaxed = true)

    private lateinit var urlShortenerListViewModel: UrlShortenerListViewModel

    private val viewStateObserver = mockk<Observer<ViewState>> { every { onChanged(any()) } just Runs }

    @Before
    fun setup() {
        urlShortenerListViewModel = UrlShortenerListViewModel(registerUrlUseCase)
    }

    @Test
    fun `GIVEN valid typedUrl WHEN UrlShortenerListViewModel calls validateTypedUrl function THEN buttonSendAvailability is true`() {
        // GIVEN
        val typedUrl = "https://sou.nu"
        // WHEN
        urlShortenerListViewModel.validateTypedUrl(typedUrl = typedUrl)
        // THEN
        assertEquals(true, urlShortenerListViewModel.buttonSendUrlIsEnable.value)
    }

    @Test
    fun `GIVEN not valid typedUrl WHEN UrlShortenerListViewModel calls validateTypedUrl function THEN buttonSendAvailability is false`() {
        // GIVEN
        val notValidTypedUrl = ""
        // WHEN
        urlShortenerListViewModel.validateTypedUrl(typedUrl = notValidTypedUrl)
        // THEN
        assertEquals(false, urlShortenerListViewModel.buttonSendUrlIsEnable.value)
    }

    @Test
    fun `GIVEN typedUrl WHEN UrlShortenerListViewModel calls registerUrl function THEN validate shortenedUrlResultList ok`() {
        // GIVEN: mocked data
        val typedUrl = "https://sou.nu"
        val originalUrl = OriginalUrl(url = typedUrl)
        val shortUrl = "https://url-shortener-nu.herokuapp.com/short/73563"
        val urlLinks = UrlLinks(originalUrl = typedUrl, shortUrl = shortUrl)
        val shortenedUrl = ShortenedUrl(urlAlias = "73563", urlLinks = urlLinks)
        coEvery { registerUrlUseCase.execute(originalUrl) } returns shortenedUrl

        urlShortenerListViewModel = UrlShortenerListViewModel(registerUrlUseCase)

        // WHEN
        urlShortenerListViewModel.registerUrl(sourceUrl = typedUrl)

        // THEN: validate shortenedUrlResultList
        urlShortenerListViewModel.shortenedUrlResultList.value?.let {
            // AND: shortenedUrlResultList must have size one
            val listSize = it.size
            assertEquals(1, listSize)
            // AND: urlShortenerItemView must have same content of mocked data
            val urlShortenerItemView = it[0]
            assertEquals(typedUrl, urlShortenerItemView.originalUrl)
            assertEquals(shortUrl, urlShortenerItemView.shortUrl)
        }
    }

    @Test
    fun `GIVEN one valid typedUrl WHEN UrlShortenerListViewModel registerUrl calls number times to same typedUrl THEN shortenedUrlResultList do not contain repeated items`() {
        // GIVEN: one valid typedUrl
        val typedUrl_1 = "https://sou.nu"
        val originalUrl_1 = OriginalUrl(url = typedUrl_1)
        val shortUrl_1 = "https://url-shortener-nu.herokuapp.com/short/73563"
        val urlLinks_1 = UrlLinks(originalUrl = typedUrl_1, shortUrl = shortUrl_1)
        val shortenedUrl_1 = ShortenedUrl(urlAlias = "73563", urlLinks = urlLinks_1)
        coEvery { registerUrlUseCase.execute(originalUrl_1) } returns shortenedUrl_1

        val urlShortenerListViewModel = UrlShortenerListViewModel(registerUrlUseCase)

        // WHEN: UrlShortenerListViewModel registerUrl calls number (here = 3x) times to same typedUrl
        urlShortenerListViewModel.registerUrl(sourceUrl = typedUrl_1)
        urlShortenerListViewModel.registerUrl(sourceUrl = typedUrl_1)
        urlShortenerListViewModel.registerUrl(sourceUrl = typedUrl_1)

        // THEN: shortenedUrlResultList do not contain repeated items
        urlShortenerListViewModel.shortenedUrlResultList.value?.let {
            val listSize = it.size
            assertEquals(1, listSize)

            val itemView = it[0]
            assertEquals(typedUrl_1, itemView.originalUrl)
            assertEquals(shortUrl_1, itemView.shortUrl)
        }
    }

    @Test
    fun `GIVEN two valid URLs WHEN UrlShortenerListViewModel calls registerUrl function THEN validate UrlShortenerItemView list order`() {
        // GIVEN: fist link to be sent
        val typedUrlSouNu = "https://sou.nu"
        val originalUrlSouNu = OriginalUrl(url = typedUrlSouNu)
        val shortUrlSouNu = "https://url-shortener-nu.herokuapp.com/short/73563"
        val urlLinksSouNu = UrlLinks(originalUrl = typedUrlSouNu, shortUrl = shortUrlSouNu)
        val shortenedUrlSouNu = ShortenedUrl(urlAlias = "73563", urlLinks = urlLinksSouNu)
        coEvery { registerUrlUseCase.execute(originalUrlSouNu) } returns shortenedUrlSouNu
        // AND: last link to be sent
        val typedUrlGoogle = "https://google.com"
        val originalUrlGoogle = OriginalUrl(url = typedUrlGoogle)
        val shortUrlGoogle = "https://url-shortener-nu.herokuapp.com/short/54876"
        val urlLinksGoogle = UrlLinks(originalUrl = typedUrlGoogle, shortUrl = shortUrlGoogle)
        val shortenedUrlGoogle = ShortenedUrl(urlAlias = "54876", urlLinks = urlLinksGoogle)
        coEvery { registerUrlUseCase.execute(originalUrlGoogle) } returns shortenedUrlGoogle

        val urlShortenerListViewModel = UrlShortenerListViewModel(registerUrlUseCase)

        // WHEN: the links are sent sequentially
        urlShortenerListViewModel.registerUrl(sourceUrl = typedUrlSouNu)
        urlShortenerListViewModel.registerUrl(sourceUrl = typedUrlGoogle)

        // THEN
        urlShortenerListViewModel.shortenedUrlResultList.value?.let {
            // shortenedUrlResultList should have the size number links sent sequentially
            val listSize = it.size
            assertEquals(2, listSize)
            // AND shortenedUrlResultList first item has the content of last link sent
            val firstListItem = it[0]
            assertEquals(typedUrlGoogle, firstListItem.originalUrl)
            assertEquals(shortUrlGoogle, firstListItem.shortUrl)
            // AND shortenedUrlResultList second item has the content of first link sent
            val lastItemItem = it[1]
            assertEquals(typedUrlSouNu, lastItemItem.originalUrl)
            assertEquals(shortUrlSouNu, lastItemItem.shortUrl)
        }
    }


    @Test
    fun `GIVEN valid typedUrl WHEN UrlShortenerListViewModel calls registerUrl function with success THEN verify ViewState sequence order`() {
        // GIVEN: valid typedUrl WHEN UrlShortenerListViewModel
        val typedUrl = "https://sou.nu"
        val originalUrl = OriginalUrl(url = typedUrl)
        val shortUrl = "https://url-shortener-nu.herokuapp.com/short/73563"
        val urlLinks = UrlLinks(originalUrl = typedUrl, shortUrl = shortUrl)
        val shortenedUrl = ShortenedUrl(urlAlias = "73563", urlLinks = urlLinks)
        coEvery { registerUrlUseCase.execute(originalUrl) } returns shortenedUrl

        urlShortenerListViewModel = UrlShortenerListViewModel(registerUrlUseCase)

        urlShortenerListViewModel.viewState.observeForever(viewStateObserver)


        // WHEN: UrlShortenerListViewModel calls registerUrl function with success
        urlShortenerListViewModel.registerUrl(sourceUrl = typedUrl)

        // THEN: verify ViewState sequence order
        verifySequence {
            viewStateObserver.onChanged(ViewState.Loading)
            viewStateObserver.onChanged(ViewState.Success)
        }
        // AND: no errors happened
        verify(exactly = 0) {
            viewStateObserver.onChanged(ViewState.Error())
        }
    }

    @Test
    fun `GIVEN NetworkException WHEN registerUrl call THEN Loading AND Error states triggered`() {
        // GIVEN: NetworkException
        val exception = NetworkException(message = "ERROR CONNECTION REFUSED")
        coEvery { registerUrlUseCase.execute(any()) } throws exception

        urlShortenerListViewModel = UrlShortenerListViewModel(registerUrlUseCase)

        urlShortenerListViewModel.viewState.observeForever(viewStateObserver)

        // WHEN: UrlShortenerListViewModel calls registerUrl function
        urlShortenerListViewModel.registerUrl(sourceUrl = "typed url")

        // THEN: verify ViewState sequence order
        verifySequence {
            viewStateObserver.onChanged(ViewState.Loading)
            viewStateObserver.onChanged(ViewState.Error(exception = exception))
        }
        // AND: can't return success state
        verify(exactly = 0) {
            viewStateObserver.onChanged(ViewState.Success)
        }
    }

    @Test
    fun `GIVEN ServerErrorException WHEN registerUrl call THEN Loading AND Error states triggered`() {
        // GIVEN: ServerErrorException
        val exception = ServerErrorException(message = "INTERNAL SERVER ERROR")
        coEvery { registerUrlUseCase.execute(any()) } throws exception

        urlShortenerListViewModel = UrlShortenerListViewModel(registerUrlUseCase)

        urlShortenerListViewModel.viewState.observeForever(viewStateObserver)

        // WHEN: UrlShortenerListViewModel calls registerUrl function
        urlShortenerListViewModel.registerUrl(sourceUrl = "typed url")

        // THEN: verify ViewState sequence order
        verifySequence {
            viewStateObserver.onChanged(ViewState.Loading)
            viewStateObserver.onChanged(ViewState.Error(exception = exception))
        }
        // AND: can't return success state
        verify(exactly = 0) {
            viewStateObserver.onChanged(ViewState.Success)
        }
    }

    @Test
    fun `GIVEN RepositoryException WHEN registerUrl call THEN Loading AND Error states triggered`() {
        // GIVEN: RepositoryException
        val exception = RepositoryException(message = "REPOSITORY EXCEPTION")
        coEvery { registerUrlUseCase.execute(any()) } throws exception

        urlShortenerListViewModel = UrlShortenerListViewModel(registerUrlUseCase)

        urlShortenerListViewModel.viewState.observeForever(viewStateObserver)

        // WHEN: UrlShortenerListViewModel calls registerUrl function
        urlShortenerListViewModel.registerUrl(sourceUrl = "typed url")

        // THEN: verify ViewState sequence order
        verifySequence {
            viewStateObserver.onChanged(ViewState.Loading)
            viewStateObserver.onChanged(ViewState.Error(exception = exception))
        }
        // AND: can't return success state
        verify(exactly = 0) {
            viewStateObserver.onChanged(ViewState.Success)
        }
    }
}

