package com.sergiocasero.commit.common.presenter

import com.sergiocasero.commit.common.model.DayItem
import com.sergiocasero.commit.common.model.TrackItem
import com.sergiocasero.commit.common.error.ErrorHandler
import com.sergiocasero.commit.common.executor.Executor
import com.sergiocasero.commit.common.mapper.toView
import com.sergiocasero.commit.common.models.DayView
import com.sergiocasero.commit.common.repository.ClientRepository
import kotlinx.coroutines.launch

class HomePresenter(
    private val repository: ClientRepository,
    view: HomeView,
    errorHandler: ErrorHandler,
    executor: Executor
) : Presenter<HomeView>(errorHandler = errorHandler, executor = executor, view = view) {

    private val days: MutableList<DayItem> = mutableListOf()

    override fun attach() {
        getDays()
    }

    private fun getDays() {
        scope.launch {
            view.showProgress()
            repository.getDays().fold(
                error = { onRetry(it) { getDays() } },
                success = { daysResponse ->
                    days.clear()
                    days.addAll(daysResponse.items)
                    view.showDays(days.mapIndexed { index, dayItem -> dayItem.toView(index) })

                    onDaySelected(days.indexOf(days.first { it.default }))
                }
            )
            view.hideProgress()
        }
    }

    fun onDaySelected(dayPos: Int) {
        scope.launch {
            view.showProgress()
            repository.getDayTracks(days[dayPos].id).fold(
                error = { onRetry(it) { onDaySelected(dayPos) } },
                success = { view.showTracks(it.tracks) }
            )
            view.hideProgress()
        }
    }

}

interface HomeView : Presenter.View {
    fun showDays(days: List<DayView>)
    fun showTracks(tracks: List<TrackItem>)
}
