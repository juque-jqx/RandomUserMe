package com.julienquievreux.data.extensions

import com.julienquievreux.data.results.Result

fun <L, R> Result<L, R>.ifSuccessful(block: (R) -> Unit): Result<L, R> {
    if (this is Result.Success) block(value)
    return this
}

fun <L, R> Result<L, R>.ifFailed(block: (L) -> Unit): Result<L, R> {
    if (this is Result.Failure) block(value)
    return this
}