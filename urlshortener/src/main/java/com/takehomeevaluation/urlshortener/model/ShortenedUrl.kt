package com.takehomeevaluation.urlshortener.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShortenedUrl(
    val urlAlias: String,
    val urlLinks: UrlLinks
) : Parcelable