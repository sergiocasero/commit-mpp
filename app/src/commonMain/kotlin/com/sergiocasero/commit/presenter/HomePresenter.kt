package com.sergiocasero.commit.presenter

import com.sergiocasero.commit.common.model.DayItem
import com.sergiocasero.commit.common.model.TrackItem
import com.sergiocasero.commit.error.ErrorHandler
import com.sergiocasero.commit.executor.Executor
import com.sergiocasero.commit.mapper.toView
import com.sergiocasero.commit.models.DayView
import com.sergiocasero.commit.repository.ClientRepository
import kotlinx.coroutines.launch

class HomePresenter(
    private val repository: ClientRepository,
    view: HomeView,
    errorHandler: ErrorHandler,
    executor: Executor
) : Presenter<HomeView>(errorHandler = errorHandler, executor = executor, view = view) {

    private val days: MutableList<DayItem> = mutableListOf()

    override fun attach() {
        scope.launch {
            view.showProgress()
            repository.getDays().fold(
                error = onError,
                success = { daysResponse ->
                    days.clear()
                    days.addAll(daysResponse.items)
                    view.showDays(days.mapIndexed { index, dayItem -> dayItem.toView(index) })
                    onDaySelected(0) // Change for today
                }
            )
            view.hideProgress()
        }
    }

    fun onDaySelected(dayPos: Int) {
        scope.launch {
            view.showProgress()
            repository.getDayTracks(days[dayPos].id).fold(
                error = onError,
                success = {
                    view.showTracks(it.tracks)
                }
            )
            view.hideProgress()
        }
    }

}

interface HomeView : Presenter.View {
    fun showDays(days: List<DayView>)
    fun showTracks(tracks: List<TrackItem>)
}
