package com.sergiocasero.commit.error

import com.sergiocasero.commit.result.Error

/*
expect class ErrorHandler {
    fun convert(error: Error): String
}
*/
interface ErrorHandler {
    fun convert(error: Error): String
}
