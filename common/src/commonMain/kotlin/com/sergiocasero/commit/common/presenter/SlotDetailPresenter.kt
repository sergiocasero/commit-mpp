package com.sergiocasero.commit.common.presenter

import com.sergiocasero.commit.common.model.Slot
import com.sergiocasero.commit.common.model.Speaker
import com.sergiocasero.commit.common.error.ErrorHandler
import com.sergiocasero.commit.common.executor.Executor
import com.sergiocasero.commit.common.navigator.Navigator
import com.sergiocasero.commit.common.repository.ClientRepository
import kotlinx.coroutines.launch

class SlotDetailPresenter(
    private val repository: ClientRepository,
    private val navigator: Navigator,
    view: SlotDetailView,
    errorHandler: ErrorHandler,
    executor: Executor
) : Presenter<SlotDetailView>(errorHandler = errorHandler, executor = executor, view = view) {

    private var isFav = false

    override fun attach() {
        getSlot(view.getSlotId())
        view.showFavUI(isFav)
    }

    private fun getSlot(slotId: Long) {
        scope.launch {
            view.showProgress()
            repository.getSlot(slotId).fold(error = { onRetry(it) { getSlot(slotId) } }, success = { view.showSlot(it) })
            view.hideProgress()
        }
    }

    fun onFavClick() {
        isFav = !isFav
        view.showFavUI(isFav)
        // TODO do something stuff on db
    }

    fun onSpeakerTwitterClick(speaker: Speaker) {
        speaker.twitterAccount?.let { navigator.openSpeakerTwitter(it) }
    }
}

interface SlotDetailView : Presenter.View {
    fun getSlotId(): Long
    fun showSlot(slot: Slot)
    fun showFavUI(isFav: Boolean)
}
