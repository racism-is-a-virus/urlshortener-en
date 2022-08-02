package com.takehomeevaluation.urlshortener.data.remote.model

import com.google.gson.annotations.SerializedName
import com.takehomeevaluation.core.interfaces.MapToModel
import com.takehomeevaluation.urlshortener.model.UrlLinks

data class UrlLinksResponse(
    @SerializedName("self")
    val originalUrl: String,
    @SerializedName("short")
    val shortUrl: String
) : MapToModel<UrlLinks> {
    override fun toModel() = UrlLinks(
        originalUrl = originalUrl,
        shortUrl = shortUrl
    )
}
