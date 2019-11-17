package com.sergiocasero.commit.presenter

import com.sergiocasero.commit.common.model.Slot
import com.sergiocasero.commit.common.model.Speaker
import com.sergiocasero.commit.error.ErrorHandler
import com.sergiocasero.commit.executor.Executor
import com.sergiocasero.commit.navigator.Navigator
import com.sergiocasero.commit.repository.ClientRepository
import kotlinx.coroutines.launch

class SlotDetailPresenter(
    private val repository: ClientRepository,
    private val navigator: Navigator,
    view: SlotDetailView,
    errorHandler: ErrorHandler,
    executor: Executor
) : Presenter<SlotDetailView>(errorHandler = errorHandler, executor = executor, view = view) {

    private var isFav = false

    private var slot: Slot? = null

    override fun attach() {
        getSlot(view.getSlotId())
    }

    private fun getSlot(slotId: Long) {
        scope.launch {
            view.showProgress()
            repository.getSlot(slotId).fold(
                error = { onRetry(it) { getSlot(slotId) } },
                success = {
                    slot = it
                    view.showSlot(it)
                    checkFav(it)
                })
            view.hideProgress()
        }
    }

    private fun checkFav(slot: Slot) {
        scope.launch {
            repository.isSlotFav(slot).fold(
                error = { onRetry(it) { checkFav(slot) } },
                success = {
                    isFav = it
                    view.showFavUI(isFav)
                }
            )
        }
    }

    fun onFavClick() {
        scope.launch {
            slot?.let {
                repository.updateFavSlot(it, !isFav).fold(
                    error = { onRetry(it) { onFavClick() } },
                    success = {
                        isFav = !isFav
                        view.showFavUI(isFav)
                    }
                )
            }
        }
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
