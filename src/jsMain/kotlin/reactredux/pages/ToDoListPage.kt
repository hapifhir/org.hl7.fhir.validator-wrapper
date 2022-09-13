package reactredux.pages

import reactredux.components.filters
import reactredux.containers.addTodo
import reactredux.containers.visibleTodoList
import react.RBuilder
import react.dom.br
import react.router.dom.NavLink
import ringui.*

fun RBuilder.toDoListPage() {

    Grid {
        Row {
            attrs { center = RowPosition.xs }

            Col {
                attrs {
                    xs = 10
                    md = 8
                    lg = 5
                }

                Island {
                    IslandHeader {
                        attrs.border = true

                        Heading {
                            +"Todo app sample"
                        }
                    }
                    IslandContent {
                        addTodo {}
                        filters()
                        visibleTodoList {}
                        br {}
                        NavLink {
                            attrs.to = "/"
                            +"Go back"
                        }
                    }
                }
            }
        }
    }
}