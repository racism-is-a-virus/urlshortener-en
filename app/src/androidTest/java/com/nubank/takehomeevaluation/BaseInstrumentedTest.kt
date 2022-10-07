package com.nubank.takehomeevaluation

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.ViewModelStore
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.nubank.takehomeevaluation.core.InstrumentedTestApplication
import com.takehomeevaluation.core.network.ApiClientBuilder
import com.takehomeevaluation.urlshortener.data.UrlShortenerRepository
import com.takehomeevaluation.urlshortener.data.remote.UrlShortenerRemoteRepository
import com.takehomeevaluation.urlshortener.data.remote.UrlShortenerService
import com.takehomeevaluation.urlshortener.navigation.UrlShortenerNavigation
import com.takehomeevaluation.urlshortener.navigation.UrlShortenerNavigationImpl
import com.takehomeevaluation.urlshortener.ui.shortenerlist.UrlShortenerListViewModel
import com.takehomeevaluation.urlshortener.usecase.RegisterUrlUseCase
import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

open class BaseInstrumentedTest {

    private val application = getInstrumentation().targetContext.applicationContext as InstrumentedTestApplication
    protected val urlShortenerRemoteRepository: UrlShortenerRemoteRepository = mockk()

    @Before
    fun baseSetup() = application.startDependencyInjection(getModule())

    @After
    fun baseAfter() {
        application.stopDependencyInjection()
    }

    private fun getModule(): List<Module> {
        return listOf(
            module {
                single {
                    ApiClientBuilder.createServiceApi(
                        serviceClass = UrlShortenerService::class.java,
                        baseUrl = BuildConfig.BASE_URL
                    )
                }
                factory { urlShortenerRemoteRepository } bind UrlShortenerRepository::class
                factory { RegisterUrlUseCase(repo = get()) }
                viewModel { UrlShortenerListViewModel(registerUrlUseCase = get()) }
                factory<UrlShortenerNavigation> { UrlShortenerNavigationImpl(get()) }
            }
        )
    }

    val navController by lazy {
        TestNavHostController(ApplicationProvider.getApplicationContext()).apply {
            setViewModelStore(ViewModelStore())
            setGraph(R.navigation.urlshortener_graph)
        }
    }

    protected inline fun <reified T : Fragment> startFragment(
        arguments: Bundle? = null,
        theme: Int = R.style.Theme_TakeHomeEvaluation,
        crossinline factory: () -> T
    ): FragmentScenario<T> = launchFragmentInContainer(arguments, theme) {
        factory().also { fragment ->
            fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                if (viewLifecycleOwner != null) {
                    Navigation.setViewNavController(fragment.requireView(), navController)
                }
            }
        }
    }
}