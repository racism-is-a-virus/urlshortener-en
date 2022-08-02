package com.takehomeevaluation.core.interfaces

// MapToModel: this interface is here to be consumed by various feature modules, like: urlshortener
interface MapToModel<Model> {
    fun toModel(): Model
}