package com.takehomeevaluation.core.interfaces

interface UseCase<Param, Return> {
    suspend fun execute(param: Param): Return
}