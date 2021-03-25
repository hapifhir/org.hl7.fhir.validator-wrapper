package ui.components.generic

import css.component.HeadingWithDropDownExplanationStyle
import kotlinx.css.Display
import kotlinx.css.display
import kotlinx.html.js.onClickFunction
import react.*
import styled.css
import styled.styledDiv
import styled.styledImg
import styled.styledSpan

external interface HeadingWithDropDownExplanationProps : RProps {
    // Heading text.
    var heading: String

    // Explanation text.
    var explanation: String
}

class HeadingWithDropDownExplanationState : RState {
    var dropDownOpen = false
}

/**
 * Sometimes it is necessary to display a brief explanation to the user, regarding a certain section or setting. This
 * need not be displayed at all times, and can be shown/hidden by clicking a drop down arrow.
 */
class HeadingWithDropDownExplanation :
    RComponent<HeadingWithDropDownExplanationProps, HeadingWithDropDownExplanationState>() {

    init {
        state = HeadingWithDropDownExplanationState()
    }

    override fun RBuilder.render() {
        styledDiv {
            css {
                +HeadingWithDropDownExplanationStyle.mainDiv
            }
            styledDiv {
                css {
                    +HeadingWithDropDownExplanationStyle.titleAndArrowDiv
                }
                attrs {
                    onClickFunction = {
                        setState {
                            dropDownOpen = !dropDownOpen
                        }
                    }
                }
                styledSpan {
                    css {
                        +HeadingWithDropDownExplanationStyle.titleHeading
                    }
                    +props.heading
                }
                styledImg {
                    css {
                        +HeadingWithDropDownExplanationStyle.arrowIcon
                    }
                    attrs {
                        src = if (state.dropDownOpen) {
                            "images/arrow_up.svg"
                        } else {
                            "images/arrow_down.svg"
                        }
                    }
                }
            }
            styledSpan {
                css {
                    +HeadingWithDropDownExplanationStyle.explanationBlock
                    display = if (state.dropDownOpen) {
                        Display.flex
                    } else {
                        Display.none
                    }
                }
                +props.explanation
            }
        }
    }
}

/**
 * Convenience method for instantiating the component.
 */
fun RBuilder.headingWithDropDownExplanation(handler: HeadingWithDropDownExplanationProps.() -> Unit): ReactElement {
    return child(HeadingWithDropDownExplanation::class) {
        this.attrs(handler)
    }
}
