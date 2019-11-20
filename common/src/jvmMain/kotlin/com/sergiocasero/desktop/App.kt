package com.sergiocasero.desktop

import com.sergiocasero.commit.common.datasource.remote.CommonRemoteDataSource
import com.sergiocasero.commit.common.model.Slot
import com.sergiocasero.commit.common.presenter.SlotDetailPresenter
import com.sergiocasero.commit.common.presenter.SlotDetailView
import com.sergiocasero.commit.common.repository.CommonClientRepository
import javafx.beans.property.SimpleStringProperty
import javafx.scene.Parent
import javafx.stage.Stage
import tornadofx.*

class Commit: App(SlotDetailScreen::class) {

    override fun start(stage: Stage) {
        super.start(stage)
        stage.width = 400.0
        stage.height = 400.0
    }
}

const val detailId: Long = 387404009
class SlotDetailScreen: View(), SlotDetailView {

    private val name = SimpleStringProperty()
    private val description = SimpleStringProperty()
    private val speakers = SimpleStringProperty()

    private val presenter = SlotDetailPresenter(
        navigator = DesktopNavigator(),
        executor = DesktopExecutor(),
        errorHandler = DesktopErrorHandler(),
        repository = CommonClientRepository(DesktopLocalDataSource(), CommonRemoteDataSource()),
        view = this
    )

    override fun onDock() {
        super.onDock()
        presenter.attach()
    }

    override val root = vbox {
        fitToParentSize()
        label(name)
        label(description)
        label(speakers)
    }

    override fun getSlotId(): Long = detailId

    override fun showSlot(slot: Slot) {
        name.value = slot.contents?.title
        description.value = slot.contents?.description
        speakers.value = slot?.contents?.speakers?.joinToString(", ") { it.name }
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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}