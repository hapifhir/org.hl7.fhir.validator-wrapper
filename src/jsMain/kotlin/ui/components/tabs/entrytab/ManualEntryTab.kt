package ui.components.tabs.entrytab

import Polyglot
import api.sendValidationRequest
import constants.FhirFormat
import css.animation.FadeIn.fadeIn
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import kotlinx.css.*
import mainScope
import model.CliContext
import model.ValidationOutcome
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import styled.StyleSheet
import styled.css
import styled.styledDiv
import ui.components.tabs.tabHeading
import ui.components.validation.issuelist.filteredIssueEntryList
import utils.assembleRequest
import utils.isJson
import utils.isXml

//TODO make this an intelligent value
private const val VALIDATION_TIME_LIMIT = 30000L

external interface ManualEntryTabProps : RProps {
    var cliContext: CliContext
    var validationOutcome: ValidationOutcome?
    var currentManuallyEnteredText: String
    var validatingManualEntryInProgress: Boolean
    var polyglot: Polyglot
    var sessionId: String

    var setValidationOutcome: (ValidationOutcome) -> Unit
    var toggleValidationInProgress: (Boolean) -> Unit
    var updateCurrentlyEnteredText: (String) -> Unit
    var setSessionId: (String) -> Unit
}

class ManualEntryTabState : RState {
}

class ManualEntryTab : RComponent<ManualEntryTabProps, ManualEntryTabState>() {
    init {
        state = ManualEntryTabState()
    }

    override fun RBuilder.render() {
        styledDiv {
            css {
                +ManualEntryTabStyle.mainContainer
            }
            tabHeading {
                text = "Code"
            }
            manualEntryTextArea {
                currentText = props.currentManuallyEnteredText
                onTextUpdate = { str ->
                    props.updateCurrentlyEnteredText(str)
                }
            }
            fileEntryButtonBar {
                onValidateRequested = {
                    validateEnteredText(props.currentManuallyEnteredText)
                }
                workInProgress = props.validatingManualEntryInProgress
            }
            props.validationOutcome?.let {
                filteredIssueEntryList {
                    validationOutcome = props.validationOutcome!!
                }
            }
        }
    }

    private fun validateEnteredText(fileContent: String) {
        props.toggleValidationInProgress(true)
        val request = assembleRequest(
            cliContext = props.cliContext,
            fileName = generateFileName(fileContent),
            fileContent = fileContent,
            fileType = FhirFormat.JSON
        ).setSessionId(props.sessionId)
        mainScope.launch {
            try {
                withTimeout(VALIDATION_TIME_LIMIT) {
                    val validationResponse = sendValidationRequest(request)
                    props.setSessionId(validationResponse.getSessionId())
                    val returnedOutcome = validationResponse.getOutcomes().map { it.setValidated(true) }
                    println("File validated\n"
                            + "filename -> " + returnedOutcome.first().getFileInfo().fileName
                            + "content -> " + returnedOutcome.first().getFileInfo().fileContent
                            + "type -> " + returnedOutcome.first().getFileInfo().fileType
                            + "Issues ::\n" + returnedOutcome.first().getMessages()
                        .joinToString { "\n" })
                    props.setValidationOutcome(returnedOutcome.first())
                    props.toggleValidationInProgress(false)
                }
            } catch (e: TimeoutCancellationException) {
                //TODO
                println("Timeout ${e.message}")
            } catch (e: Exception) {
                //TODO
                println("Exception ${e.message}")
            } finally {
                props.toggleValidationInProgress(false)
            }
        }
    }
    private fun generateFileName(fileContent: String): String {
        return when {
            isJson(fileContent) -> "manually_entered_file.json"
            isXml(fileContent) -> "manually_entered_file.xml"
            else -> /*TODO*/""
        }
    }
}

/**
 * CSS
 */
object ManualEntryTabStyle : StyleSheet("ManualEntryTabStyle") {
    val mainContainer by css {
        display = Display.flex
        flexDirection = FlexDirection.column
        justifyContent = JustifyContent.start
        overflowY = Overflow.auto
        padding(horizontal = 32.px, vertical = 16.px)
        fadeIn()
    }
}