package com.sergiocasero.commit.common.result

sealed class Error {
    object NoInternet : Error()
    object NotFound: Error()
    object InvalidCredentials: Error()
    object Default: Error()
}

object Success
