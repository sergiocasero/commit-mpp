package com.sergiocasero.commit.presenter

import com.sergiocasero.commit.error.ErrorHandler
import com.sergiocasero.commit.executor.Executor

class TalksListPresenter(
    view: View,
    errorHandler: ErrorHandler,
    executor: Executor
) : Presenter<TalksListPresenter.View>(errorHandler = errorHandler, executor = executor, view = view) {

    override fun attach() {
        view.showTrackId(view.obtainTrackId().toString())
    }

    interface View : Presenter.View {
        fun showTrackId(id: String)
        fun obtainTrackId(): Int
    }
}