package com.takehomeevaluation.urlshortener.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.takehomeevaluation.urlshortener.databinding.UrlshortenerMainFragmentBinding
import com.takehomeevaluation.urlshortener.navigation.UrlShortenerNavigation
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

/*
 The UrlShortenerMainFragment is not needed. It's here for the purpose of exemplifying an entry point only,
 interfacing with other modules that call the urlshortener module.
 */
internal class UrlShortenerMainFragment : Fragment() {

    private var _binding: UrlshortenerMainFragmentBinding? = null
    private val binding get() = _binding!!
    private val urlShortenerNavigation: UrlShortenerNavigation by inject { parametersOf(findNavController()) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = UrlshortenerMainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        openUrlShortenerList()
    }

    private fun openUrlShortenerList() = urlShortenerNavigation.openUrlShortenerList()
}