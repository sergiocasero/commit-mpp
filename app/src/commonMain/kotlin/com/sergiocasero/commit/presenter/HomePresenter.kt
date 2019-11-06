package com.sergiocasero.commit.presenter

import com.sergiocasero.commit.common.model.DaysResponse
import com.sergiocasero.commit.error.ErrorHandler
import com.sergiocasero.commit.executor.Executor
import com.sergiocasero.commit.repository.ClientRepository
import kotlinx.coroutines.launch

class HomePresenter(
    private val repository: ClientRepository,
    view: HomeView,
    errorHandler: ErrorHandler,
    executor: Executor
) : Presenter<HomeView>(errorHandler = errorHandler, executor = executor, view = view) {

    override fun attach() {
        scope.launch {
            view.showProgress()

            repository.getDays().fold(
                error = onError,
                success = {
                    view.showDays(it)
                }
            )

            view.hideProgress()
        }
    }

}

interface HomeView : Presenter.View {
    fun showDays(days: DaysResponse)
}
