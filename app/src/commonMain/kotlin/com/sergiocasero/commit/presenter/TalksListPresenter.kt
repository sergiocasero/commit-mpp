package com.sergiocasero.commit.presenter

import com.sergiocasero.commit.common.model.Slot
import com.sergiocasero.commit.error.ErrorHandler
import com.sergiocasero.commit.executor.Executor

class TalksListPresenter(
    view: TalksView,
    errorHandler: ErrorHandler,
    executor: Executor
) : Presenter<TalksView>(errorHandler = errorHandler, executor = executor, view = view) {

    override fun attach() {

    }

    fun onSlotClicked(slot: Slot) {

    }

}

interface TalksView : Presenter.View {
    fun obtainTrackId(): Long
    fun showSlots(slots: List<Slot>)
}