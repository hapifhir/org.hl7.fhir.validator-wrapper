package uicomponents

import api.sendValidationRequest
import constants.FhirFormat
import css.TabBarStyle
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.css.*
import model.CliContext
import model.FileInfo
import org.w3c.files.File
import react.*
import styled.*
import utils.assembleRequest

val mainScope = MainScope()

/**
 * We need to store the OperationOutcome externally, and pass it as an attribute to the OperationOutcomeList component.
 * React calls these attributes props. If props change in React, the framework will take care of re-rendering of the
 * page for us.
 */
external interface ManualEnterTabProps : RProps {
    var active: Boolean
    var onValidate: (List<FileInfo>) -> Unit
}

class ManualEnterTabState : RState {
    var files: MutableList<FileInfo> = mutableListOf()
}

class ManualEnterTab : RComponent<ManualEnterTabProps, ManualEnterTabState>() {

    init {
        state = ManualEnterTabState()
    }

    override fun RBuilder.render() {
        styledDiv {
            css {
                display = if (props.active) Display.flex else Display.none
                +TabBarStyle.body
            }
            resourceEntryComponent {
                onSubmit = {
                    setState {
                        files.add(FileInfo().setFileName("").setFileContent(it).setFileType(FhirFormat.JSON.code))
                    }
                    props.onValidate(state.files)
                }
            }
        }
    }
}

/**
 * We can use lambdas with receivers to make the component easier to work with.
 * To include this component in a layout, someone can simply add:
 *
 *              bottomMenu {
 *
 *              }
 */
fun RBuilder.manualEnterTab(handler: ManualEnterTabProps.() -> Unit): ReactElement {
    return child(ManualEnterTab::class) {
        this.attrs(handler)
    }
}