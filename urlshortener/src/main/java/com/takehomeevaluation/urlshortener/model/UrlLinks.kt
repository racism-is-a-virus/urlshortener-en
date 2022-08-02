package com.takehomeevaluation.urlshortener.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UrlLinks(
    val originalUrl: String,
    val shortUrl: String
) : Parcelable
