package com.sergiocasero.frontend.view

import com.sergiocasero.commit.common.datasource.remote.CommonRemoteDataSource
import com.sergiocasero.commit.common.model.TrackItem
import com.sergiocasero.commit.common.models.DayView
import com.sergiocasero.commit.common.presenter.HomePresenter
import com.sergiocasero.commit.common.presenter.HomeView
import com.sergiocasero.commit.common.repository.CommonClientRepository
import com.sergiocasero.frontend.error.JsErrorHandler
import com.sergiocasero.frontend.executor.JsExecutor
import kotlinx.html.id
import react.RBuilder
import react.RProps
import react.dom.div
import react.dom.img
import react.dom.span
import react.setState

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
        div("main") {
            div("toolbar") {
                img { attrs.src = "http://sergiocasero.es/votlin_logo.png" }
                span { +"Votlin" }
            }
            div("tabs") {

            }
            div {
                attrs.id = "talks"
            }

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

    override fun showDays(days: List<DayView>) {
        println(days)
    }

    override fun showTracks(tracks: List<TrackItem>) {
        println(tracks)
    }

    private fun active(active: Boolean): String = if (active) "active" else ""


    private fun updateActiveTabAndLoadData(
        all: Boolean = false,
        development: Boolean = false,
        business: Boolean = false,
        maker: Boolean = false
    ) {
        setState {

        }
    }
}

class HomeState : ScreenState {
    override var progress: Boolean = false
}

interface HomeProps : RProps {
    var onTalkSelected: (Int) -> Unit
}

fun RBuilder.home(onTalkSelected: (Int) -> Unit) = child(HomeScreen::class) {
    attrs.onTalkSelected = onTalkSelected
}
