package com.sergiocasero.desktop

import com.sergiocasero.commit.common.datasource.remote.CommonRemoteDataSource
import com.sergiocasero.commit.common.model.Slot
import com.sergiocasero.commit.common.presenter.SlotDetailPresenter
import com.sergiocasero.commit.common.presenter.SlotDetailView
import com.sergiocasero.commit.common.repository.CommonClientRepository
import javafx.beans.property.SimpleStringProperty
import tornadofx.View
import tornadofx.fitToParentSize
import tornadofx.label
import tornadofx.vbox

class SlotDetailScreen2: View(), SlotDetailView {

    private val name = SimpleStringProperty("")
    private val bio = SimpleStringProperty("")
    private val speakers = SimpleStringProperty("")

    private val presenter = SlotDetailPresenter(
        navigator = DesktopNavigator(),
        errorHandler = DesktopErrorHandler(),
        executor = DesktopExecutor(),
        view = this,
        repository = CommonClientRepository(DesktopLocalDataSource(), CommonRemoteDataSource())
    )

    override val root = vbox {
        fitToParentSize()

        label(name)
        label(bio)

        label(speakers)
    }

    override fun onDock() {
        presenter.attach()
    }

    override fun getSlotId(): Long = 387404009

    override fun showSlot(slot: Slot) {
       name.value = slot.contents?.title ?: ""
       bio.value = slot.contents?.description ?: ""
       speakers.value = slot.contents?.speakers?.joinToString(", ") { it.name }
    }

    override fun showFavUI(isFav: Boolean) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showProgress() {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideProgress() {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showRetry(error: String, f: () -> Unit) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showError(error: String) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}