package com.takehomeevaluation.urlshortener.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
 data class OriginalUrl(
    val url: String
) : Parcelable