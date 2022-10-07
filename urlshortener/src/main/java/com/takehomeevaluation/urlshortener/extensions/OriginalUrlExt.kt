package com.takehomeevaluation.urlshortener.extensions

import com.takehomeevaluation.urlshortener.data.remote.model.OriginalUrlInput
import com.takehomeevaluation.urlshortener.model.OriginalUrl

internal fun OriginalUrl.toInput() = OriginalUrlInput(url = url)