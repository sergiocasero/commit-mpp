package com.sergiocasero.commit.common.presenter

import com.sergiocasero.commit.common.model.Slot
import com.sergiocasero.commit.common.error.ErrorHandler
import com.sergiocasero.commit.common.executor.Executor
import com.sergiocasero.commit.common.navigator.Navigator
import com.sergiocasero.commit.common.repository.ClientRepository
import kotlinx.coroutines.launch

class TalksListPresenter(
    private val repository: ClientRepository,
    private val navigator: Navigator,
    view: TalksView,
    errorHandler: ErrorHandler,
    executor: Executor
) : Presenter<TalksView>(errorHandler = errorHandler, executor = executor, view = view) {

    override fun attach() {
        getTrack()
    }

    private fun getTrack() {
        scope.launch {
            view.showProgress()
            repository.getTrack(view.obtainTrackId()).fold(error = { onRetry(it) { getTrack() } }, success = { view.showSlots(it.slots) })
            view.hideProgress()
        }
    }

    fun onSlotClicked(slot: Slot) {
        /*
        * We should use navigator but it's not possible right now because we need the ios Framework to do
        * the correct navigation. When we implement actual navigator class we cannot cast the ViewController to
        * SlotDetailVC (Slot detail's view controller) because we don't have access to Swift project files.
        * We use the view, because that view implementation have access to others Swift view controllers.
        * */
        view.navigateToSlotDetail(slot.id)
    }

}

interface TalksView : Presenter.View {
    fun obtainTrackId(): Long
    fun showSlots(slots: List<Slot>)
    fun navigateToSlotDetail(slotId: Long)
}
