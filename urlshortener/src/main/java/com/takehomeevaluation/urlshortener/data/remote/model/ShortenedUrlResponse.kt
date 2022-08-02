package com.takehomeevaluation.urlshortener.data.remote.model

import com.google.gson.annotations.SerializedName
import com.takehomeevaluation.core.interfaces.MapToModel
import com.takehomeevaluation.urlshortener.model.ShortenedUrl

data class ShortenedUrlResponse(
    @SerializedName("alias")
    val urlAlias: String,
    @SerializedName("_links")
    val urlLinks: UrlLinksResponse
) : MapToModel<ShortenedUrl> {
    override fun toModel() = ShortenedUrl(
        urlAlias = urlAlias,
        urlLinks = urlLinks.toModel()
    )
}