package com.sergiocasero.frontend.executor

import com.sergiocasero.commit.common.executor.Executor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class JsExecutor(override val main: CoroutineDispatcher = Dispatchers.Main) : Executor
