package ui.components.tabs.entrytab

import Polyglot
import api.sendValidationRequest

import css.animation.FadeIn.fadeIn
import css.const.BORDER_GRAY
import css.text.TextStyle

import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import kotlinx.css.*
import kotlinx.css.properties.border
import mainScope
import model.BundleValidationRule
import model.CliContext
import model.PackageInfo
import model.ValidationOutcome
import react.*
import react.dom.attrs

import styled.*
import ui.components.options.presetSelect
import ui.components.tabs.heading

import ui.components.validation.issuelist.filteredIssueEntryList
import utils.assembleRequest
import utils.isJson
import utils.isXml

//TODO make this an intelligent value
private const val VALIDATION_TIME_LIMIT =  60000L

external interface ManualEntryTabProps : Props {
    var cliContext: CliContext
    var validationOutcome: ValidationOutcome?
    var currentManuallyEnteredText: String
    var validatingManualEntryInProgress: Boolean
    var polyglot: Polyglot
    var sessionId: String

    var setValidationOutcome: (ValidationOutcome) -> Unit
    var toggleValidationInProgress: (Boolean) -> Unit
    var updateCurrentlyEnteredText: (String) -> Unit
    var updateCliContext: (CliContext) -> Unit
    var updateIgPackageInfoSet: (Set<PackageInfo>) -> Unit
    var updateExtensionSet: (Set<String>) -> Unit
    var updateProfileSet: (Set<String>) -> Unit
    var updateBundleValidationRuleSet: (Set<BundleValidationRule>) -> Unit
    var setSessionId: (String) -> Unit
}

class ManualEntryTabState : State {
    var displayingError: Boolean = false
    var errorMessage: String = ""
    var ohShitYouDidIt = false
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
            heading {
                text = props.polyglot.t("manual_entry_title")
            }
            if (state.ohShitYouDidIt) {
                styledIframe {
                    css {
                        +ManualEntryTabStyle.ken
                    }
                    attrs {
                        src = "https://player.vimeo.com/video/148751763?autoplay=1&loop=1&autopause=0"
                    }
                }
            } else {
                manualEntryTextArea {
                    currentText = props.currentManuallyEnteredText
                    placeholderText = props.polyglot.t("manual_entry_place_holder")
                    onTextUpdate = { str ->
                        props.updateCurrentlyEnteredText(str)
                        if (state.displayingError) {
                            setState {
                                displayingError = false
                            }
                        }
                    }
                }
            }
            styledDiv {
                css {
                    +ManualEntryTabStyle.buttonBar
                }
                manualEntryValidateButton {
                    validateText = props.polyglot.t("validate_button")
                    onValidateRequested = {
                        if (props.currentManuallyEnteredText.isNotEmpty()) {
                            validateEnteredText(props.currentManuallyEnteredText)
                        } else {
                            val newErrorMessage = props.polyglot.t("manual_entry_error")
                            setState {
                                errorMessage = newErrorMessage
                                displayingError = true
                            }
                        }
                    }
                    workInProgress = props.validatingManualEntryInProgress
                }
                styledDiv{
                    css {
                        +ManualEntryTabStyle.buttonBarDivider
                    }
                }
                presetSelect{
                    cliContext = props.cliContext
                    updateCliContext = props.updateCliContext
                    updateIgPackageInfoSet = props.updateIgPackageInfoSet
                    updateExtensionSet = props.updateExtensionSet
                    updateProfileSet = props.updateProfileSet
                    updateBundleValidationRuleSet = props.updateBundleValidationRuleSet
                    setSessionId = props.setSessionId
                    polyglot = props.polyglot
                }
            }
            if (state.displayingError) {
                styledSpan {
                    css {
                        +TextStyle.manualEntryFailMessage
                    }
                    +state.errorMessage
                }
            }
            props.validationOutcome?.let {
                filteredIssueEntryList {
                    polyglot = props.polyglot
                    validationOutcome = props.validationOutcome!!
                }
            }
        }
    }

    private fun validateEnteredText(fileContent: String) {
        setState {
            displayingError = false
            ohShitYouDidIt = false
        }
        props.toggleValidationInProgress(true)
        println("clicontext :: sv == ${props.cliContext.getSv()}, version == ${props.cliContext.getTargetVer()}, languageCode == ${props.cliContext.getLanguageCode()}")
        val request = assembleRequest(
            cliContext = props.cliContext,
            fileName = generateFileName(fileContent),
            fileContent = fileContent,
            fileType = null
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
                setState {
                    if (props.currentManuallyEnteredText.contains("Mark is super dorky")) {
                        ohShitYouDidIt = true
                        props.updateCurrentlyEnteredText("Ken is super dorky.")
                        errorMessage = "Never gonna give you up, never gonna let you down, never gonna run around..."
                        displayingError = true
                    } else {
                        errorMessage = props.polyglot.t("manual_entry_cannot_parse_exception")
                        displayingError = true
                    }
                }
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
    val buttonBar by css {
        display = Display.inlineFlex
        flexDirection = FlexDirection.row
        alignItems = Align.center
    }
    val buttonBarDivider by css {
        width = 16.px
    }
    val ken by css {
        display = Display.flex
        flexDirection = FlexDirection.column
        justifyContent = JustifyContent.stretch
        alignContent = Align.stretch
        overflowY = Overflow.auto
        minHeight = 600.px
        border(width = 1.px, color = BORDER_GRAY, style = BorderStyle.solid)
    }
}