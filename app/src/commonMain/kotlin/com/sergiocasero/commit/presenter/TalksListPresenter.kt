package com.sergiocasero.commit.presenter

import com.sergiocasero.commit.common.model.Slot
import com.sergiocasero.commit.error.ErrorHandler
import com.sergiocasero.commit.executor.Executor
import com.sergiocasero.commit.navigator.Navigator
import com.sergiocasero.commit.repository.ClientRepository
import kotlinx.coroutines.launch

class TalksListPresenter(
    private val repository: ClientRepository,
    private val navigator: Navigator,
    view: TalksView,
    errorHandler: ErrorHandler,
    executor: Executor
) : Presenter<TalksView>(errorHandler = errorHandler, executor = executor, view = view) {

    override fun attach() {
        scope.launch {
            view.showProgress()
            repository.getTrack(view.obtainTrackId()).fold(
                error = onError,
                success = { track ->
                    view.showSlots(track.slots)
                }
            )
            view.hideProgress()
        }
    }

    fun onSlotClicked(slot: Slot) {
        navigator.navigateToSlotDetail(slot.id)
    }

}

interface TalksView : Presenter.View {
    fun obtainTrackId(): Long
    fun showSlots(slots: List<Slot>)
}