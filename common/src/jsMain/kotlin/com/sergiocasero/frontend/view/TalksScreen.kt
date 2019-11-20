package com.sergiocasero.frontend.view

import com.sergiocasero.commit.common.datasource.remote.CommonRemoteDataSource
import com.sergiocasero.commit.common.model.Slot
import com.sergiocasero.commit.common.model.TrackItem
import com.sergiocasero.commit.common.presenter.TalksListPresenter
import com.sergiocasero.commit.common.presenter.TalksView
import com.sergiocasero.commit.common.repository.CommonClientRepository
import com.sergiocasero.frontend.JsNavigator
import com.sergiocasero.frontend.error.JsErrorHandler
import com.sergiocasero.frontend.executor.JsExecutor
import react.RBuilder
import react.RProps
import react.dom.div
import react.dom.img
import react.dom.span
import react.setState

class TalksScreen : RootScreen<TalksProps, TalksState, TalksView>(), TalksView {

    init {
        state = TalksState()
    }

    override val presenter: TalksListPresenter = TalksListPresenter(
        view = this,
        errorHandler = JsErrorHandler(),
        repository = CommonClientRepository(
            local = com.sergiocasero.frontend.app.LocalDataSource(),
            remote = CommonRemoteDataSource()
        ),
        executor = JsExecutor(),
        navigator = JsNavigator()
    )

    override fun RBuilder.render() {
        div("track") {

            state.slots.forEach {
                div("slot") {
                    div("time") {
                        span("start") { +it.start }
                        span { +it.end }
                    }
                    div("info") {
                        span("title") { +"${it.contents?.title}" }
                        span("speakers") { +"${it.contents?.speakers?.joinToString(", ") { it.name }}" }
                    }
                }
            }
            // div("days") {
            //     state.forEach { day ->
            //         a { +day.title }
            //     }
            // }

            if (state.progress) {
                div("progress") {
                    img("progress") {
                        attrs.src =
                            "https://mir-s3-cdn-cf.behance.net/project_modules/disp/35771931234507.564a1d2403b3a.gif"
                    }
                }
            }
        }
    }

    override fun showError(error: String) {
        println(error)
    }

    override fun showRetry(error: String, f: () -> Unit) {
        // TODO
    }

    override fun obtainTrackId(): Long = props.trackId

    override fun showSlots(slots: List<Slot>) {
        setState {
            this.slots.clear()
            this.slots.addAll(slots)
        }
    }

    override fun navigateToSlotDetail(slotId: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
//
        // private fun active(activTalksStatee: Boolean): String = if (active) "active" else ""
}

class TalksState : ScreenState {
    override var progress: Boolean = false
    val slots = mutableListOf<Slot>()
}

interface TalksProps : RProps {
    var trackId: Long
}

fun RBuilder.talks(trackId: Long) = child(TalksScreen::class) {
    attrs.trackId = trackId
}
