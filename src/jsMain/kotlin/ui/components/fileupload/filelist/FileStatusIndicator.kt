package ui.components.fileupload.filelist

import css.component.fileupload.filelist.FileStatusIndicatorStyle
import model.IssueSeverity
import model.ValidationOutcome
import react.*
import styled.css
import styled.styledDiv
import styled.styledImg
import utils.getHighestIssueSeverity

external interface FileStatusIndicatorProps : RProps {
    var validationOutcome: ValidationOutcome
}

class FileStatusIndicatorState : RState {}

/**
 * Graphical indicator for outcome of validation process.
 */
class FileStatusIndicator : RComponent<FileStatusIndicatorProps, FileStatusIndicatorState>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                if (!props.validationOutcome.isValidated()) {
                    if (props.validationOutcome.isValidating()) {
                        +FileStatusIndicatorStyle.loadingIndicator
                    } else {
                        +FileStatusIndicatorStyle.indicatorNoStatus
                    }
                } else {
                    when (getHighestIssueSeverity(props.validationOutcome.getMessages())) {
                        IssueSeverity.INFORMATION -> +FileStatusIndicatorStyle.indicatorInformation
                        IssueSeverity.WARNING -> +FileStatusIndicatorStyle.indicatorWarning
                        IssueSeverity.ERROR -> +FileStatusIndicatorStyle.indicatorError
                        IssueSeverity.FATAL -> +FileStatusIndicatorStyle.indicatorFatal
                        else -> +FileStatusIndicatorStyle.indicatorNoStatus
                    }
                }
            }
            if (props.validationOutcome.isValidated()) {
                styledDiv {
                    css {
                        +FileStatusIndicatorStyle.imageContainer
                    }
                    styledImg {
                        css {
                            +FileStatusIndicatorStyle.indicatorImage
                        }
                        attrs {
                            src = when (getHighestIssueSeverity(props.validationOutcome.getMessages())) {
                                IssueSeverity.INFORMATION -> "images/validation_success_white.png"
                                IssueSeverity.WARNING -> "images/validation_warning_white.png"
                                IssueSeverity.ERROR -> "images/validation_error_white.png"
                                IssueSeverity.FATAL -> "images/validation_fatal_white.png"
                                else -> ""
                            }
                        }
                    }
                }
            }
        }
    }
}

fun RBuilder.fileStatusIndicator(handler: FileStatusIndicatorProps.() -> Unit): ReactElement {
    return child(FileStatusIndicator::class) {
        this.attrs(handler)
    }
}
