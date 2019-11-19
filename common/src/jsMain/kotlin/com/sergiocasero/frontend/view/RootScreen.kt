package com.sergiocasero.frontend.view

import com.sergiocasero.commit.common.presenter.Presenter
import react.RComponent
import react.RProps
import react.RState
import react.setState

abstract class RootScreen<P : RProps, S : ScreenState, out V : Presenter.View> : RComponent<P, S>(), Presenter.View {

    abstract val presenter: Presenter<V>

    override fun componentDidMount() {
        presenter.attach()
    }

    override fun showProgress() {
        setState { progress = true }
    }

    override fun hideProgress() {
        setState { progress = false }
    }
}

interface ScreenState : RState {
    var progress: Boolean
}
