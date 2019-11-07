package com.sergiocasero.commit.executor

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

actual class Executor {
    actual val main: CoroutineDispatcher = Dispatchers.Main
}