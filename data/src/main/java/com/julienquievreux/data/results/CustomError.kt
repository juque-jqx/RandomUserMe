package com.julienquievreux.data.results

sealed class CustomError {
    data object NetworkError : CustomError()
    data object DatabaseError : CustomError()
}