package com.sergiocasero.commit.common.error

import com.sergiocasero.commit.common.result.Error

/*
expect class ErrorHandler {
    fun convert(error: Error): String
}
*/
interface ErrorHandler {
    fun convert(error: Error): String
}
