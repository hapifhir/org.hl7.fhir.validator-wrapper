package reactredux.containers

import kotlinx.html.ButtonType
import kotlinx.html.js.onSubmitFunction
import reactredux.actions.AddTodo
import store
import org.w3c.dom.HTMLInputElement
import react.*
import react.dom.div
import react.dom.form
import react.redux.rConnect
import redux.WrapperAction
import ringui.*

@JsExport
class AddTodo(props: Props) : RComponent<Props, State>(props) {
    private val inputRef = createRef<HTMLInputElement>()
    override fun RBuilder.render() {
        div {
            form {
                attrs.onSubmitFunction = { event ->
                    event.preventDefault()
                    inputRef.current!!.let {
                        if (it.value.trim().isNotEmpty()) {
                            store.dispatch(AddTodo(it.value))
                            it.value = ""
                        }
                    }
                }

                Row {
                    attrs.baseline = RowPosition.xs
                    attrs.center = RowPosition.xs
                    Col {
                        Input {
                            attrs.size = "L"
                            attrs.inputRef = inputRef
                            attrs.label = "Task name"
                        }
                    }
                    Col {
                        Button {
                            attrs.asDynamic().type = ButtonType.submit
                            +"Add Todo"
                        }
                    }
                }
            }
        }
    }
}


val addTodo: ComponentClass<Props> =
    rConnect<AddTodo, WrapperAction>()(reactredux.containers.AddTodo::class.js.unsafeCast<ComponentClass<Props>>())