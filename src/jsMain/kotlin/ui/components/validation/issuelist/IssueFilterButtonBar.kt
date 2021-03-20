package ui.components.validation.issuelist

import kotlinx.css.*
import model.MessageFilter
import react.*
import styled.StyleSheet
import styled.css
import styled.styledDiv
import ui.components.buttons.optionButton

external interface IssueFilterButtonBarProps : RProps {
    var messageFilter: MessageFilter
    var onUpdated: (MessageFilter) -> Unit
}

/**
 * Component displaying the horizontal list of buttons for file upload and validation
 */
class IssueFilterButtonBar : RComponent<IssueFilterButtonBarProps, RState>() {

    override fun RBuilder.render() {
        styledDiv {
            css {
                +IssueFilterButtonBarStyle.buttonBarContainer
            }
            optionButton {
                label = "Fatals"
                active = props.messageFilter.showFatal
                onSelected = {
                    props.messageFilter.showFatal = !props.messageFilter.showFatal
                    props.onUpdated(props.messageFilter)
                }
            }
            styledDiv {
                css {
                    width = 16.px
                }
            }
            optionButton {
                label = "Errors"
                active = props.messageFilter.showError
                onSelected = {
                    props.messageFilter.showError = !props.messageFilter.showError
                    props.onUpdated(props.messageFilter)
                }
            }
            styledDiv {
                css {
                    width = 16.px
                }
            }
            optionButton {
                label = "Warnings"
                active = props.messageFilter.showWarning
                onSelected = {
                    props.messageFilter.showWarning = !props.messageFilter.showWarning
                    props.onUpdated(props.messageFilter)
                }
            }
            styledDiv {
                css {
                    width = 16.px
                }
            }
            optionButton {
                label = "Information"
                active = props.messageFilter.showInfo
                onSelected = {
                    props.messageFilter.showInfo = !props.messageFilter.showInfo
                    props.onUpdated(props.messageFilter)
                }
            }
        }
    }
}

/**
 * React Component Builder
 */
fun RBuilder.issueFilterButtonBar(handler: IssueFilterButtonBarProps.() -> Unit): ReactElement {
    return child(IssueFilterButtonBar::class) {
        this.attrs(handler)
    }
}

/**
 * CSS
 */
object IssueFilterButtonBarStyle : StyleSheet("IssueFilterButtonBarStyle", isStatic = true) {
    val buttonBarContainer by css {
        display = Display.inlineFlex
        flexDirection = FlexDirection.row
        alignItems = Align.center
        padding(vertical = 16.px)
    }
}