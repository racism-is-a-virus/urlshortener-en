package com.nubank.takehomeevaluation.shortenerlist

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isNotEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.nubank.takehomeevaluation.BaseInstrumentedTest
import com.nubank.takehomeevaluation.R
import com.nubank.takehomeevaluation.utils.RecyclerViewItemCountAssertion
import com.nubank.takehomeevaluation.utils.RecyclerViewMatcher.Companion.withRecyclerView
import com.takehomeevaluation.urlshortener.model.OriginalUrl
import com.takehomeevaluation.urlshortener.model.ShortenedUrl
import com.takehomeevaluation.urlshortener.model.UrlLinks
import com.takehomeevaluation.urlshortener.ui.shortenerlist.UrlShortenerListFragment
import io.mockk.coEvery
import org.hamcrest.CoreMatchers.not
import org.junit.Test


class UrlShortenerListFragmentTest : BaseInstrumentedTest() {

    @Test
    fun given_UrlShortenerListFragment_WHEN_startFragment_THEN_validate_initial_views_state() {
        // GIVEN
        val fragmentUrlShortenerList = UrlShortenerListFragment()

        // WHEN
        startFragment { fragmentUrlShortenerList }

        // THEN: button send viewed
        onView(withId(R.id.button_send_url)).check(matches(isDisplayed()))
        // AND: button send disabled
        onView(withId(R.id.button_send_url)).check(matches(isNotEnabled()))
        // AND: empty edittext field
        onView(withId(R.id.original_url_edit_text)).check(matches(isDisplayed())).check(matches(withText("")))
        // AND: url list empty (recyclerView) no visible
        onView(withId(R.id.urlshortener_list)).check(matches(not(isDisplayed())))
        // AND: recyclerView with zero elements
        onView(withId(R.id.urlshortener_list)).check(RecyclerViewItemCountAssertion(0))
    }

    @Test
    fun given_UrlShortenerListFragment_initial_views_state_WHEN_buttonSendUrl_clicked_THEN_urlshortener_list_not_displayed_an_with_empty_list() {
        // GIVEN
        startFragment { UrlShortenerListFragment() }

        // WHEN
        onView(withId(R.id.button_send_url)).perform(click())

        // THEN: url list no visible
        onView(withId(R.id.urlshortener_list)).check(matches(not(isDisplayed())))
        // AND: recyclerView with zero elements
        onView(withId(R.id.urlshortener_list)).check(RecyclerViewItemCountAssertion(0))
    }

    @Test
    fun given_typedUrl_at_original_url_edit_textbutton_WHEN_button_send_url_clicked_THEN_check_visibility_content_urlshortener_list() {
        // GIVEN
        val originalUrl = OriginalUrl(url = "https://sou.nu")
        val shortenedUrl = ShortenedUrl(
            urlAlias = "73563",
            urlLinks = UrlLinks(
                originalUrl = "https://sou.nu",
                shortUrl = "https://url-shortener-nu.herokuapp.com/short/73563"
            )

        )
        coEvery { urlShortenerRemoteRepository.registerUrl(originalUrl) } returns shortenedUrl

        startFragment { UrlShortenerListFragment() }

        val typedUrl = "https://sou.nu"
        onView(withId(R.id.original_url_edit_text)).perform(replaceText(typedUrl))

        // WHEN
        onView(withId(R.id.button_send_url)).perform(click())

        // THEN
        onView(withId(R.id.urlshortener_scroll_view)).check(matches(isDisplayed()))
        onView(withId(R.id.urlshortener_list)).check(RecyclerViewItemCountAssertion(1))
        onView(
            withRecyclerView(R.id.urlshortener_list).atPositionOnView(0, R.id.url_title_item)
        ).check(matches(withText("URL"))).check(matches(isDisplayed()))
        onView(
            withRecyclerView(R.id.urlshortener_list).atPositionOnView(0, R.id.url_content_item)
        ).check(matches(withText("https://sou.nu"))).check(matches(isDisplayed()))
        onView(
            withRecyclerView(R.id.urlshortener_list).atPositionOnView(0, R.id.short_title_item)
        ).check(matches(withText("Short"))).check(matches(isDisplayed()))
        onView(
            withRecyclerView(R.id.urlshortener_list).atPositionOnView(0, R.id.short_content_item)
        ).check(matches(withText("https://url-shortener-nu.herokuapp.com/short/73563"))).check(matches(isDisplayed()))
    }

    @Test
    fun given_typedUrl_at_original_url_edit_text_WHEN_double_click_at_button_send_url_to_same_url_THEN_dont_add_two_elements_same_content() {
        // GIVEN
        val originalUrl = OriginalUrl(url = "https://sou.nu")
        val shortenedUrl = ShortenedUrl(
            urlAlias = "73563",
            urlLinks = UrlLinks(
                originalUrl = "https://sou.nu",
                shortUrl = "https://url-shortener-nu.herokuapp.com/short/73563"
            )

        )
        coEvery { urlShortenerRemoteRepository.registerUrl(originalUrl) } returns shortenedUrl

        startFragment { UrlShortenerListFragment() }

        val typedUrl = "https://sou.nu"
        onView(withId(R.id.original_url_edit_text)).perform(replaceText(typedUrl))

        // WHEN: double click performed
        onView(withId(R.id.button_send_url)).perform(click())
        onView(withId(R.id.button_send_url)).perform(click())

        // THEN: list is displayed
        onView(withId(R.id.urlshortener_scroll_view)).check(matches(isDisplayed()))
        // AND: validate that a new element was not added to the list with the same content
        onView(withId(R.id.urlshortener_list)).check(RecyclerViewItemCountAssertion(1))
        onView(
            withRecyclerView(R.id.urlshortener_list).atPositionOnView(0, R.id.url_title_item)
        ).check(matches(withText("URL"))).check(matches(isDisplayed()))
        onView(
            withRecyclerView(R.id.urlshortener_list).atPositionOnView(0, R.id.url_content_item)
        ).check(matches(withText("https://sou.nu"))).check(matches(isDisplayed()))
        onView(
            withRecyclerView(R.id.urlshortener_list).atPositionOnView(0, R.id.short_title_item)
        ).check(matches(withText("Short"))).check(matches(isDisplayed()))
        onView(
            withRecyclerView(R.id.urlshortener_list).atPositionOnView(0, R.id.short_content_item)
        ).check(matches(withText("https://url-shortener-nu.herokuapp.com/short/73563"))).check(matches(isDisplayed()))
    }

    @Test
    fun given_two_different_links_WHEN_these_links_are_sent_THEN_validate_list_diplayed() {
        // GIVEN: mocked data of first link
        var originalUrl = OriginalUrl(url = "https://sou.nu")
        var shortenedUrl = ShortenedUrl(
            urlAlias = "73563",
            urlLinks = UrlLinks(
                originalUrl = "https://sou.nu",
                shortUrl = "https://url-shortener-nu.herokuapp.com/short/73563"
            )

        )
        coEvery { urlShortenerRemoteRepository.registerUrl(originalUrl) } returns shortenedUrl

        startFragment { UrlShortenerListFragment() }

        // AND: first link typed: https://sou.nu
        var typedUrl = "https://sou.nu"
        onView(withId(R.id.original_url_edit_text)).perform(replaceText(typedUrl))
        // AND: first link sent
        onView(withId(R.id.button_send_url)).perform(click())

        // AND: mocked data of second link
        originalUrl = OriginalUrl(url = "https://nubank.com.br")
        shortenedUrl = ShortenedUrl(
            urlAlias = "58518",
            urlLinks = UrlLinks(
                originalUrl = "https://nubank.com.br",
                shortUrl = "https://url-shortener-nu.herokuapp.com/short/58518"
            )

        )
        coEvery { urlShortenerRemoteRepository.registerUrl(originalUrl) } returns shortenedUrl

        // AND: second link typed: https://sou.nu
        typedUrl = "https://nubank.com.br"
        onView(withId(R.id.original_url_edit_text)).perform(replaceText(typedUrl))
        // AND: second link sent
        onView(withId(R.id.button_send_url)).perform(click())

        // WHEN: validate screen content
        // THEN: validate list diplayed in screen
        onView(withId(R.id.urlshortener_scroll_view)).check(matches(isDisplayed()))
        // AND: number of list elements are equal of different links sent
        onView(withId(R.id.urlshortener_list)).check(RecyclerViewItemCountAssertion(2))
        // AND: the first item on the list was the last to be sent
        onView(
            withRecyclerView(R.id.urlshortener_list).atPositionOnView(0, R.id.url_title_item)
        ).check(matches(withText("URL"))).check(matches(isDisplayed()))
        onView(
            withRecyclerView(R.id.urlshortener_list).atPositionOnView(0, R.id.url_content_item)
        ).check(matches(withText("https://nubank.com.br"))).check(matches(isDisplayed()))
        onView(
            withRecyclerView(R.id.urlshortener_list).atPositionOnView(0, R.id.short_title_item)
        ).check(matches(withText("Short"))).check(matches(isDisplayed()))
        onView(
            withRecyclerView(R.id.urlshortener_list).atPositionOnView(0, R.id.short_content_item)
        ).check(matches(withText("https://url-shortener-nu.herokuapp.com/short/58518"))).check(matches(isDisplayed()))
        // AND: the second item on the list was the first to be sent
        onView(
            withRecyclerView(R.id.urlshortener_list).atPositionOnView(1, R.id.url_title_item)
        ).check(matches(withText("URL"))).check(matches(isDisplayed()))
        onView(
            withRecyclerView(R.id.urlshortener_list).atPositionOnView(1, R.id.url_content_item)
        ).check(matches(withText("https://sou.nu"))).check(matches(isDisplayed()))
        onView(
            withRecyclerView(R.id.urlshortener_list).atPositionOnView(1, R.id.short_title_item)
        ).check(matches(withText("Short"))).check(matches(isDisplayed()))
        onView(
            withRecyclerView(R.id.urlshortener_list).atPositionOnView(1, R.id.short_content_item)
        ).check(matches(withText("https://url-shortener-nu.herokuapp.com/short/73563"))).check(matches(isDisplayed()))
    }
}



