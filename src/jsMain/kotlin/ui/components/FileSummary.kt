package ui.components

import css.component.FileSummaryStyle
import css.text.TextStyle
import kotlinx.css.Display
import kotlinx.css.display
import kotlinx.html.js.onClickFunction
import model.ValidationOutcome
import react.*
import styled.*

external interface FileSummaryProps : RProps {
    var validationOutcome: ValidationOutcome
    var active: Boolean
    var onClose: () -> Unit
}

class FileSummaryState : RState {
    var codeDisplay = true
}

class FileSummaryComponent : RComponent<FileSummaryProps, FileSummaryState>() {

    init {
        state = FileSummaryState()
    }

    override fun RBuilder.render() {
        styledDiv {
            css {
                +FileSummaryStyle.overlay
                display = if (props.active) Display.flex else Display.none
            }
            styledDiv {
                css {
                    +FileSummaryStyle.modalContent
                }
                styledDiv {
                    css {
                        +FileSummaryStyle.titleBar
                    }
                    styledP {
                        css {
                            +TextStyle.h2
                            +FileSummaryStyle.filename
                        }
                        +props.validationOutcome.getFileInfo().fileName
                    }
                    styledImg {
                        css {
                            +FileSummaryStyle.button
                        }
                        attrs {
                            src = "images/code-view.svg"
                            onClickFunction = {
                                setState {
                                    codeDisplay = true
                                }
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
                                setState {
                                    codeDisplay = false
                                }
                            }
                        }
                    }
                    styledImg {
                        css {
                            +FileSummaryStyle.button
                        }
                        attrs {
                            src = "images/close.svg"
                            onClickFunction = {
                                props.onClose()
                            }
                        }
                    }
                }
                styledHr {
                    css {
                        +FileSummaryStyle.horizontalRule
                    }
                }
                if (state.codeDisplay) {
                    fileIssueCodeDisplayComponent {
                        validationOutcome = props.validationOutcome
                    }
                } else {
                    fileIssueListDisplayComponent {
                        validationOutcome = props.validationOutcome
                    }
                }
            }
        }
    }
}

fun RBuilder.fileSummaryComponent(handler: FileSummaryProps.() -> Unit): ReactElement {
    return child(FileSummaryComponent::class) {
        this.attrs(handler)
    }
}
