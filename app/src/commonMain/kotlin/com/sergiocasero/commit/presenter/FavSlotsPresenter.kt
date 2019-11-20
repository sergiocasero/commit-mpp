package com.sergiocasero.commit.presenter

import com.sergiocasero.commit.common.model.Slot
import com.sergiocasero.commit.error.ErrorHandler
import com.sergiocasero.commit.executor.Executor
import com.sergiocasero.commit.navigator.Navigator
import com.sergiocasero.commit.repository.ClientRepository
import kotlinx.coroutines.launch

class FavSlotsPresenter(
    private val repository: ClientRepository,
    private val navigator: Navigator,
    view: FavsSlotsView,
    errorHandler: ErrorHandler,
    executor: Executor
) : Presenter<FavsSlotsView>(errorHandler = errorHandler, executor = executor, view = view) {

    override fun attach() {
        getFavsSlot()
    }

    private fun getFavsSlot() {
        scope.launch {
            view.showProgress()
            repository.getFavSlots()
                .fold(error = { onRetry(it) { getFavsSlot() } }, success = { view.showSlots(it) })
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

interface FavsSlotsView : Presenter.View {
    fun showSlots(slots: List<Slot>)
    fun navigateToSlotDetail(slotId: Long)
}