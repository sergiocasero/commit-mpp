package com.sergiocasero.commit.presenter

import com.sergiocasero.commit.error.ErrorHandler
import com.sergiocasero.commit.executor.Executor

class HomePresenter(
    view: HomeView,
    errorHandler: ErrorHandler,
    executor: Executor
) : Presenter<HomeView>(errorHandler = errorHandler, executor = executor, view = view) {

    override fun attach() {

    }

}

interface HomeView : Presenter.View {

}