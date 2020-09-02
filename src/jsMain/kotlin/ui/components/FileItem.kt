package ui.components

import css.FileItemStyle
import css.TextStyle
import kotlinx.css.*
import kotlinx.html.js.onClickFunction
import kotlinx.html.js.onMouseOutFunction
import kotlinx.html.js.onMouseOverFunction
import model.FileInfo
import model.IssueSeverity
import model.ValidationOutcome
import react.*
import styled.*
import utils.getMessageTypeCounts
import utils.getMostSevereValidationSeverity

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
                        +FileItemStyle.indicatorNoStatus
                    } else {
                        when (getMostSevereValidationSeverity(props.validationOutcome.getMessages())) {
                            IssueSeverity.INFORMATION -> +FileItemStyle.indicatorInformation
                            IssueSeverity.WARNING -> +FileItemStyle.indicatorWarning
                            IssueSeverity.ERROR -> +FileItemStyle.indicatorError
                            IssueSeverity.FATAL -> +FileItemStyle.indicatorFatal
                            IssueSeverity.NULL -> +FileItemStyle.indicatorGood
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
            fileName = props.validationOutcome.getFileInfo().fileName
            fileContent = props.validationOutcome.getFileInfo().fileContent
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
