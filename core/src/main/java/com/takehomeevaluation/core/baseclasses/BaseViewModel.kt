package com.takehomeevaluation.core.baseclasses

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.takehomeevaluation.core.ViewState
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class BaseViewModel : ViewModel() {

    private val _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState> = _viewState

    fun doAsyncWork(work: suspend () -> Unit) {
        viewModelScope.launch {
            _viewState.postValue(ViewState.Loading)
            runCatching {
                work()
                _viewState.postValue(ViewState.Success)
            }.onFailure {
                it as Exception
                Timber.e(it)
                _viewState.postValue(ViewState.Error(exception = it))
            }
        }
    }
}