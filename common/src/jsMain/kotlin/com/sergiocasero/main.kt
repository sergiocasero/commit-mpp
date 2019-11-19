package com.sergiocasero

import com.sergiocasero.frontend.app.app
import react.dom.render
import kotlin.browser.document
import kotlin.browser.window

fun main() {
    window.onload = {
        render(document.getElementById("app")) {
            window.alert("hello")
            app()
        }
    }
}
