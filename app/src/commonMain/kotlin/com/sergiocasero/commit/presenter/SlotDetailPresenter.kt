package com.sergiocasero.commit.presenter

import com.sergiocasero.commit.common.model.Slot
import com.sergiocasero.commit.error.ErrorHandler
import com.sergiocasero.commit.executor.Executor
import com.sergiocasero.commit.repository.ClientRepository
import kotlinx.coroutines.launch

class SlotDetailPresenter(
    private val repository: ClientRepository,
    view: SlotDetailView,
    errorHandler: ErrorHandler,
    executor: Executor
) : Presenter<SlotDetailView>(errorHandler = errorHandler, executor = executor, view = view) {

    override fun attach() {
        getSlot(387404009)
    }

    private fun getSlot(slotId: Long) {
        scope.launch {
            view.showProgress()

            repository.getSlot(slotId).fold(
                error = onError,
                success = { view.showSlot(it) }
            )

            view.hideProgress()
        }
    }

}

interface SlotDetailView : Presenter.View {
    fun getSlotId(): Long
    fun showSlot(slot: Slot)
}
