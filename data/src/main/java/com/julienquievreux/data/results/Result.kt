package com.julienquievreux.data.results

sealed class Result<out FailureType, out SuccessType> {
    data class Failure<out FailureType>(val value: FailureType) : Result<FailureType, Nothing>()
    data class Success<out SuccessType>(val value: SuccessType) : Result<Nothing, SuccessType>()
}