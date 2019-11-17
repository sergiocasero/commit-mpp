package com.sergiocasero.commit.common.presenter

import com.sergiocasero.commit.common.result.Error
import com.sergiocasero.commit.common.error.ErrorHandler
import com.sergiocasero.commit.common.executor.Executor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

/**
 * Presenter
 */
abstract class Presenter<out V : Presenter.View>(
    protected val errorHandler: ErrorHandler,
    executor: Executor,
    val view: V
) {

    private val job = SupervisorJob()

    protected val scope = CoroutineScope(job + executor.main)

    protected fun onRetry(error: Error, retry: () -> Unit): Unit =  view.showRetry(errorHandler.convert(error)) { retry() }

    abstract fun attach()

    fun detach() = job.cancel()

    interface View {
        fun showProgress()
        fun hideProgress()
        fun showRetry(error: String, f: () -> Unit)
        fun showError(error: String)
    }
}

