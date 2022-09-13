package reactredux.components

import kotlinx.css.marginLeft
import kotlinx.css.px
import kotlinx.html.js.onClickFunction
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import styled.css
import styled.styledButton

external interface LinkProps : RProps {
    var active: Boolean
    var onClick: () -> Unit
}

@JsExport
class Link(props: LinkProps) : RComponent<LinkProps, RState>(props) {
    override fun RBuilder.render() {
        styledButton {
            attrs.onClickFunction = { props.onClick() }
            attrs.disabled = props.active
            css {
                marginLeft = 4.px
            }
            children()
        }
    }
}