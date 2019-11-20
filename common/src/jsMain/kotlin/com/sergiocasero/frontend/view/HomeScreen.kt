package com.sergiocasero.frontend.view

import com.sergiocasero.commit.common.datasource.remote.CommonRemoteDataSource
import com.sergiocasero.commit.common.model.Track
import com.sergiocasero.commit.common.model.TrackItem
import com.sergiocasero.commit.common.models.DayView
import com.sergiocasero.commit.common.presenter.HomePresenter
import com.sergiocasero.commit.common.presenter.HomeView
import com.sergiocasero.commit.common.repository.CommonClientRepository
import com.sergiocasero.frontend.error.JsErrorHandler
import com.sergiocasero.frontend.executor.JsExecutor
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.html.id
import kotlinx.html.js.onClickFunction
import react.RBuilder
import react.RProps
import react.dom.a
import react.dom.div
import react.dom.img
import react.dom.span
import react.setState
import kotlin.browser.window

class HomeScreen : RootScreen<HomeProps, HomeState, HomeView>(), HomeView {

    init {
        state = HomeState()
    }

    override fun componentDidMount() {
        super.componentDidMount()
    }

    override val presenter: HomePresenter = HomePresenter(
        view = this,
        errorHandler = JsErrorHandler(),
        repository = CommonClientRepository(
            local = com.sergiocasero.frontend.app.LocalDataSource(),
            remote = CommonRemoteDataSource()
        ),
        executor = JsExecutor()
    )

    override fun RBuilder.render() {
        div("home") {
            div("toolbar") {
                img { attrs.src = "https://2018.commit-conf.com/android-chrome-512x512.png" }
                span { +"Commit 2019" }
            }
            div("tabs") {
                state.tracks.forEach { track ->
                    a {
                        +track.name
                        attrs.onClickFunction = {
                            console.log("hello!! ${track.id}")
                            setState { this.currentTrack = 0L }
                            window.setTimeout( {setState { this.currentTrack = track.id } }, 100)
                        }
                    }
                }
            }

            when (state.currentTrack != 0L) {
                true -> talks(trackId = state.currentTrack)
            }

            div("days") {
                state.days.forEach { day ->
                    a {
                        +day.title
                        attrs.onClickFunction = { presenter.onDaySelected(dayPos = day.pos) }
                        if (day.selected) {
                            span { +"selected" }
                        }
                    }
                }
            }

            if (state.progress) {
                div("progress") {
                    img("progress") {
                        attrs.src = "https://mir-s3-cdn-cf.behance.net/project_modules/disp/35771931234507.564a1d2403b3a.gif"
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

    override fun showDays(days: List<DayView>) {
        setState {
            this.days.clear()
            this.days.addAll(days)
        }
    }

    override fun showTracks(tracks: List<TrackItem>) {        setState {
            this.tracks.clear()
            this.tracks.addAll(tracks)
            this.currentTrack = 0L
        }
        window.setTimeout( {setState { this.currentTrack = tracks.first().id } }, 100)
    }

    private fun active(active: Boolean): String = if (active) "active" else ""
}

class HomeState : ScreenState {
    override var progress: Boolean = false
    val days = mutableListOf<DayView>()
    val tracks = mutableListOf<TrackItem>()
    var currentTrack = 0L
    var previousTrack = 0L
}

interface HomeProps : RProps {
    var onTalkSelected: (Int) -> Unit
}

fun RBuilder.home(onTalkSelected: (Int) -> Unit) = child(HomeScreen::class) {
    attrs.onTalkSelected = onTalkSelected
}
