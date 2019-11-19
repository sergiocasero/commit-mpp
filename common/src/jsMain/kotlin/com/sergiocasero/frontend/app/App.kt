package com.sergiocasero.frontend.app

import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div

abstract class App : RComponent<RProps, AppState>() {

    init {
        state = AppState()
    }

    override fun RBuilder.render() {
        div("app") {
            +"hello my friend"
            // when (state.screen) {
            //     Screen.SPLASH -> splash { setState { screen = it } }
            //     Screen.HOME -> home {
            //         setState {
            //             talkId = it
            //             screen = Screen.DETAIL
            //         }
            //     }
            //     Screen.DETAIL -> detail(state.talkId) { setState { screen = it } }
            // }
        }
    }
}

class AppState : RState {
    var screen: String = ""
    var talkId: Int = 0
}

fun RBuilder.app() = child(App::class) {}
