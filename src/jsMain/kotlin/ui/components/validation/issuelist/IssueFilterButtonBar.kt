package ui.components.validation.issuelist

import Polyglot
import kotlinx.css.*
import model.MessageFilter
import react.*
import styled.StyleSheet
import styled.css
import styled.styledDiv
import ui.components.buttons.labelledSwitch
import ui.components.buttons.optionButton

external interface IssueFilterButtonBarProps : Props {
    var messageFilter: MessageFilter
    var onUpdated: (MessageFilter) -> Unit
    var polyglot: Polyglot
}

/**
 * Component displaying the horizontal list of buttons for file upload and validation
 */
class IssueFilterButtonBar : RComponent<IssueFilterButtonBarProps, State>() {

    override fun RBuilder.render() {
        styledDiv {
            css {
                +IssueFilterButtonBarStyle.buttonBarContainer
            }
            labelledSwitch {
                label = props.polyglot.t("validation_fatals")
                active = props.messageFilter.showFatal
                onSelected = {
                    props.messageFilter.showFatal = it
                    props.onUpdated(props.messageFilter)
                }
            }
            styledDiv {
                css {
                    width = 16.px
                }
            }
            labelledSwitch {
                label = props.polyglot.t("validation_errors")
                active = props.messageFilter.showError
                onSelected = {
                    props.messageFilter.showError = it
                    props.onUpdated(props.messageFilter)
                }
            }
            styledDiv {
                css {
                    width = 16.px
                }
            }
            labelledSwitch {
                label = props.polyglot.t("validation_warnings")
                active = props.messageFilter.showWarning
                onSelected = {
                    props.messageFilter.showWarning = it
                    props.onUpdated(props.messageFilter)
                }
            }
            styledDiv {
                css {
                    width = 16.px
                }
            }
            labelledSwitch {
                label = props.polyglot.t("validation_info")
                active = props.messageFilter.showInfo
                onSelected = {
                    props.messageFilter.showInfo = it
                    props.onUpdated(props.messageFilter)
                }
            }
        }
    }
}

/**
 * React Component Builder
 */
fun RBuilder.issueFilterButtonBar(handler: IssueFilterButtonBarProps.() -> Unit) {
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