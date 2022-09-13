package reactredux.components

import react.*
import ringui.*

external interface LinkProps : PropsWithChildren {
    var active: Boolean
    var onClick: () -> Unit
}

@JsExport
class Link(props: LinkProps) : RComponent<LinkProps, State>(props) {
    override fun RBuilder.render() {

        Button {
            attrs.onMouseDown = { props.onClick() }
            attrs.active = props.active
            props.children()
        }
    }
}