package com.sergiocasero.commit.common.executor

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class AndroidExecutor: Executor {
    override val main: CoroutineDispatcher = Dispatchers.Main
}
