package com.sergiocasero.commit.presenter

import com.sergiocasero.commit.error.ErrorHandler
import com.sergiocasero.commit.executor.Executor

class HomePresenter(
    view: HomeView,
    errorHandler: ErrorHandler,
    executor: Executor
) : Presenter<HomeView>(errorHandler = errorHandler, executor = executor, view = view) {

    companion object {
        private const val TRACKS_NUMBER = 12
    }

    override fun attach() {
        view.showTracks(TRACKS_NUMBER)
    }

}

interface HomeView : Presenter.View {
    fun showTracks(tracks: Int)
}