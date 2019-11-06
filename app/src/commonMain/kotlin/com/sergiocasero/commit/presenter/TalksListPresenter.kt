package com.sergiocasero.commit.presenter

import com.sergiocasero.commit.error.ErrorHandler
import com.sergiocasero.commit.executor.Executor

class TalksListPresenter(
    view: TalksView,
    errorHandler: ErrorHandler,
    executor: Executor
) : Presenter<TalksView>(errorHandler = errorHandler, executor = executor, view = view) {

    override fun attach() {
        view.showTrackId(view.obtainTrackId().toString())
    }

}

interface TalksView : Presenter.View {
    fun showTrackId(id: String)
    fun obtainTrackId(): Long
}