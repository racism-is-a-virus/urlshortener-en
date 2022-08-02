package com.takehomeevaluation.core

sealed class ViewState {
    object Loading : ViewState()
    object Success : ViewState()
    data class Error(val errorMessage: String? = null, val exception: Exception? = null) : ViewState()
}
