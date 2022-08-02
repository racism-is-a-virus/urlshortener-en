package com.takehomeevaluation.urlshortener.navigation

import androidx.navigation.NavController
import com.takehomeevaluation.urlshortener.R

class UrlShortenerNavigationImpl(private val navController: NavController) : UrlShortenerNavigation {
    override fun openUrlShortenerList() {
        navController.navigate(R.id.action_mainFragment_to_urlShortenerListFragment)
    }
}
