package reactredux.components

import react.Props
import reactredux.pages.toDoListPage
import react.RBuilder
import react.createElement

import react.dom.div
import react.dom.h1
import react.fc
import react.router.Route
import react.router.Routes
import react.router.dom.*

private const val TODO_LIST_PATH = "/todolist"

fun RBuilder.app() = fc<Props> {
    BrowserRouter {
        Routes {
            Route {
                attrs.path = "/"

                attrs.element = createElement {
                    div {
                        h1 {
                            +"Kotlin React + React-Dom + Redux + React-Redux + React-Router Example"
                        }
                        NavLink {
                            attrs.to = TODO_LIST_PATH
                            +"Go to todo list"
                        }
                    }
                }
            }
            Route {
                attrs.path = TODO_LIST_PATH

                attrs.element = createElement {
                    toDoListPage()
                }
            }
        }
    }
}


