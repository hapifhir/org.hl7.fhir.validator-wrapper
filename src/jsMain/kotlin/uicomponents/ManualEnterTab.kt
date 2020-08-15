package uicomponents

import api.sendValidationRequest
import constants.FhirFormat
import css.TabBarStyle
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.css.*
import model.CliContext
import model.FileInfo
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
    var cliContext: CliContext
}

class ManualEnterTab : RComponent<ManualEnterTabProps, RState>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                display = if (props.active) Display.flex else Display.none
                +TabBarStyle.body
                flex(flexBasis = 100.pct)
            }
            resourceEntryComponent {
                onSubmit = {
                    val request = assembleRequest(
                        props.cliContext,
                        FileInfo().setFileName("Temp").setFileContent(it).setFileType(FhirFormat.JSON.code)
                    )
                    mainScope.launch {
                        val returnedOutcome = sendValidationRequest(request)
                        setState {
                            // Only one returned outcome in single submitted validation operation
                            println("setting state")
                            for (issue in returnedOutcome[0].getIssues()) {
                                println("${issue.getSeverity()} :: ${issue.getDetails()}")
                            }
                            // Do something with the outcome here
                        }
                    }
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