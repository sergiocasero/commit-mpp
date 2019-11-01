package com.sergiocasero.commit.presenter

import com.sergiocasero.commit.error.ErrorHandler
import com.sergiocasero.commit.executor.Executor

class HomePresenter(
    view: View,
    errorHandler: ErrorHandler,
    executor: Executor
) : Presenter<HomePresenter.View>(errorHandler = errorHandler, executor = executor, view = view) {

    override fun attach() {

    }

    interface View : Presenter.View {

    }
}