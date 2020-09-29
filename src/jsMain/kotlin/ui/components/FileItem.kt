package ui.components

import css.component.FileItemStyle
import css.text.TextStyle
import css.widget.FileStatusIndicator
import css.widget.Spinner
import kotlinx.css.Display
import kotlinx.css.display
import kotlinx.css.opacity
import kotlinx.html.js.onClickFunction
import kotlinx.html.js.onMouseOutFunction
import kotlinx.html.js.onMouseOverFunction
import model.FileInfo
import model.IssueSeverity
import model.ValidationOutcome
import react.*
import styled.*
import utils.getHighestIssueSeverity
import utils.getMessageTypeCounts

external interface FileItemProps : RProps {
    var validationOutcome: ValidationOutcome
    var onDelete: (FileInfo) -> Unit
}

class FileItemState : RState {
    var summaryActive: Boolean = false
    var toolTipVisible: Boolean = false
}

class FileItemComponent : RComponent<FileItemProps, FileItemState>() {
    override fun RBuilder.render() {
        styledLi {
            css {
                +FileItemStyle.listItem
            }
            styledSpan {
                css {
                    if (!props.validationOutcome.isValidated()) {
                        if (props.validationOutcome.isValidating()) {
                            +Spinner.loadingIconDark
                        } else {
                            +FileStatusIndicator.indicatorNoStatus
                        }
                    } else {
                        when (getHighestIssueSeverity(props.validationOutcome.getMessages())) {
                            IssueSeverity.INFORMATION -> +FileStatusIndicator.indicatorInformation
                            IssueSeverity.WARNING -> +FileStatusIndicator.indicatorWarning
                            IssueSeverity.ERROR -> +FileStatusIndicator.indicatorError
                            IssueSeverity.FATAL -> +FileStatusIndicator.indicatorFatal
                            IssueSeverity.NULL -> +FileStatusIndicator.indicatorGood
                        }
                    }
                }

                attrs {
                    onMouseOverFunction = {
                        setState {
                            if (props.validationOutcome.isValidated()) {
                                toolTipVisible = true
                            }
                        }
                    }
                    onMouseOutFunction = {
                        setState {
                            toolTipVisible = false
                        }
                    }
                }

                styledDiv {
                    css {
                        +FileItemStyle.toolTipContainer
                        if (state.toolTipVisible) {
                            display = Display.inlineBlock
                            opacity = 1
                        } else {
                            display = Display.none
                            opacity = 0
                        }
                    }
                    styledPre {
                        css {
                            +TextStyle.toolTipText
                            +FileItemStyle.toolTipOutput
                        }
                        val (info, warning, error, fatal) = getMessageTypeCounts(props.validationOutcome.getMessages())
                        +"information: $info\nwarnings: $warning\nerrors: $error\nfatals: $fatal"
                    }
                }
            }
            styledP {
                +props.validationOutcome.getFileInfo().fileName
                css {
                    +FileItemStyle.titleField
                    +TextStyle.h3
                }
                attrs {
                    onClickFunction = {
                        setState {
                            summaryActive = true
                        }
                    }
                }
            }
            styledImg {
                css {
                    +FileItemStyle.typeImage
                }
                attrs {
                    src = "images/delete.svg"
                    onClickFunction = {
                        setState {
                            props.onDelete(props.validationOutcome.getFileInfo())
                        }
                    }
                }
            }
        }
        fileSummaryComponent {
            active = state.summaryActive
            validationOutcome = props.validationOutcome
            onClose = {
                setState {
                    summaryActive = false
                }
            }
        }
    }
}

fun RBuilder.fileItemComponent(handler: FileItemProps.() -> Unit): ReactElement {
    return child(FileItemComponent::class) {
        this.attrs(handler)
    }
}
