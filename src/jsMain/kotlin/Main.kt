import kotlinx.browser.document

import react.dom.render
import react.redux.provider
//import reactredux.components.app
//import reactredux.reducers.State
//import reactredux.reducers.rootReducer

import redux.createStore
import redux.rEnhancer

//val store = createStore(::rootReducer, State(), rEnhancer())


fun main() {
    document.getElementById("root")?.innerHTML = "Hello, Kotlin/JS!"
}
/*
fun main() {
    val rootDiv = document.getElementById("root")!!
    render(rootDiv) {
        provider(store) {
            app()
        }
    }
}*/