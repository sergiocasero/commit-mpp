package com.sergiocasero.commit.executor

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class Executor {
    val main: CoroutineDispatcher = Dispatchers.Main
}
