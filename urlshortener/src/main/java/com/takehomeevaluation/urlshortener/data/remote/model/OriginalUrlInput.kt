package com.takehomeevaluation.urlshortener.data.remote.model

import com.google.gson.annotations.SerializedName
import com.takehomeevaluation.core.interfaces.MapToModel
import com.takehomeevaluation.urlshortener.model.OriginalUrl

data class OriginalUrlInput(
    @SerializedName("url")
    val url: String
) : MapToModel<OriginalUrl> {
    override fun toModel() = OriginalUrl(url = url)
}
