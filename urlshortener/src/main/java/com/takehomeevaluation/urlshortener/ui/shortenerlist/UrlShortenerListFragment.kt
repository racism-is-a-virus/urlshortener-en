package com.takehomeevaluation.urlshortener.ui.shortenerlist

import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.takehomeevaluation.core.ViewState.Error
import com.takehomeevaluation.core.ViewState.Loading
import com.takehomeevaluation.core.ViewState.Success
import com.takehomeevaluation.core.baseclasses.BaseFragment
import com.takehomeevaluation.core.extensions.closeKeyBoard
import com.takehomeevaluation.urlshortener.databinding.UrlshortenerListFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class UrlShortenerListFragment :
    BaseFragment<UrlshortenerListFragmentBinding>(UrlshortenerListFragmentBinding::inflate) {

    private val viewModel: UrlShortenerListViewModel by viewModel()

    override fun setupView() {
        setupButtonSendUrl()
        setupUrlEditText()
        setupAdapter()
        setupViewsVisibility(progressBarVisible = false)
    }

    override fun addObservers(owner: LifecycleOwner) {
        with(viewModel) {
            viewState.observe(owner) {
                when (it) {
                    is Loading -> setupViewsVisibility(progressBarVisible = true)
                    is Success -> setupViewsVisibility(progressBarVisible = false)
                    is Error -> handleError(error = it)
                }
            }

            shortenedUrlResultList.observe(owner) {
                refreshList(it)
            }

            buttonSendUrlIsEnable.observe(owner) {
                binding.buttonSendUrl.isEnabled = it
            }
        }
    }

    private fun handleError(error: Error) {
        val message = error.exception?.message ?: ""
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        setupViewsVisibility(progressBarVisible = false)
    }

    private fun setupAdapter() {
        with(binding.urlshortenerList) {
            layoutManager = LinearLayoutManager(context)
            adapter = UrlShortenerListAdapter()
            addItemDecoration(
                DividerItemDecoration(this.context, RecyclerView.VERTICAL)
            )
        }
    }

    private fun refreshList(urlShortenerViewItemList: List<UrlShortenerItemView>) {
        getUrlShortenerListAdapter().submitItemList(urlShortenerViewItemList)
    }

    private fun getUrlShortenerListAdapter() = binding.urlshortenerList.adapter as UrlShortenerListAdapter

    private fun setupButtonSendUrl() {
        with(binding.buttonSendUrl) {
            isEnabled = false
            setOnClickListener {
                loadData(param = binding.originalUrlEditText.text.toString())
            }
        }
    }

    private fun setupUrlEditText() {
        with(binding.originalUrlEditText) {
            addTextChangedListener {
                viewModel.validateTypedUrl(it.toString())
            }
            setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) return@setOnFocusChangeListener
                v.closeKeyBoard()
            }
        }
    }

    private fun setupViewsVisibility(progressBarVisible: Boolean) {
        with(binding) {
            progressBar.isVisible = progressBarVisible
            buttonSendUrl.isVisible = !progressBarVisible
            originalUrlEditText.isVisible = !progressBarVisible
            urlshortenerListTitle.isVisible = !progressBarVisible
            binding.urlshortenerScrollView.isVisible = !progressBarVisible
        }
    }

    override fun <String> loadData(param: String) = viewModel.registerUrl(sourceUrl = param)
}
