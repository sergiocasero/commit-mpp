package com.sergiocasero.commit.presenter

import com.sergiocasero.commit.error.ErrorHandler
import com.sergiocasero.commit.executor.Executor
import com.sergiocasero.commit.common.result.Error
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

/**
 * Presenter
 */
abstract class Presenter<out V : Presenter.View>(
    protected val errorHandler: ErrorHandler,
    executor: Executor,
    val view: V) {

    private val job = SupervisorJob()

    protected val scope = CoroutineScope(job + executor.main)

    protected val onError: (Error) -> Unit = { view.showError(errorHandler.convert(it)) }

    abstract fun attach()

    fun detach() = job.cancel()

    interface View {
        fun showProgress()
        fun hideProgress()
        fun showError(error: String)
        fun showError(errorId: Int)
        fun showMessage(message: String)
        fun showMessage(messageId: Int)
    }
}

