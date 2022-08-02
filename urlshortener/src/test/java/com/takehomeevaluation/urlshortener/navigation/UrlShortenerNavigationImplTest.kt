package com.takehomeevaluation.urlshortener.navigation

import androidx.navigation.NavController
import com.takehomeevaluation.urlshortener.R
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class UrlShortenerNavigationImplTest {

    private lateinit var urlShortenerNavigation: UrlShortenerNavigation
    private val navController: NavController = mockk(relaxed = true)

    @Before
    fun setUp() {
        urlShortenerNavigation = UrlShortenerNavigationImpl(navController = navController)
    }

    @Test
    fun `GIVEN UrlShortenerNavigation WHEN openUrlShortenerList THEN action_mainFragment_to_urlShortenerListFragment called`() {
        //WHEN
        urlShortenerNavigation.openUrlShortenerList()

        //THEN
        verify(exactly = 1) {
            navController.navigate(R.id.action_mainFragment_to_urlShortenerListFragment)
        }
    }
}