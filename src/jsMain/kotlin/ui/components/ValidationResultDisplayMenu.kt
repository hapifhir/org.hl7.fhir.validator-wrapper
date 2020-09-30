package ui.components

import css.component.FileSummaryStyle
import css.component.ValidationResultDisplayMenuStyle
import css.text.TextStyle
import kotlinx.html.js.onClickFunction
import react.*
import styled.css
import styled.styledDiv
import styled.styledImg
import styled.styledP

external interface ValidationResultDisplayMenuProps : RProps {
    var title: String
    var closeButton: Boolean
    var codeDisplay: () -> Unit
    var listDisplay: () -> Unit
    var close: () -> Unit
}

class ValidationResultDisplayMenuComponent : RComponent<ValidationResultDisplayMenuProps, RState>() {

    override fun RBuilder.render() {
        styledDiv {
            css {
                +FileSummaryStyle.titleBar
            }
            styledP {
                css {
                    +TextStyle.h2
                    +ValidationResultDisplayMenuStyle.titleField
                }
                +props.title
            }
            styledImg {
                css {
                    +FileSummaryStyle.button
                }
                attrs {
                    src = "images/code-view.svg"
                    onClickFunction = {
                        props.codeDisplay()
                    }
                }
            }
            styledImg {
                css {
                    +FileSummaryStyle.button
                }
                attrs {
                    src = "images/list-view.svg"
                    onClickFunction = {
                        props.listDisplay()
                    }
                }
            }
            if (props.closeButton) {
                styledImg {
                    css {
                        +FileSummaryStyle.button
                    }
                    attrs {
                        src = "images/close.svg"
                        onClickFunction = {
                            println("on close called")
                            props.close()
                        }
                    }
                }
            }
        }
    }
}

fun RBuilder.validationResultDisplayMenuComponent(handler: ValidationResultDisplayMenuProps.() -> Unit): ReactElement {
    return child(ValidationResultDisplayMenuComponent::class) {
        this.attrs(handler)
    }
}

