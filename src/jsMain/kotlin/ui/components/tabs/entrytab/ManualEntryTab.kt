package ui.components.tabs.entrytab

import Polyglot
import api.sendValidationRequest
import context.LocalizationContext
import model.Preset

import css.animation.FadeIn.fadeIn
import css.const.BORDER_GRAY
import css.text.TextStyle

import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import kotlinx.css.*
import kotlinx.css.properties.border
import mainScope
import model.*
import react.*


import styled.*
import ui.components.options.presetSelect
import ui.components.tabs.heading

import ui.components.validation.validationOutcomeContainer
import utils.*

//TODO make this an intelligent value
private const val VALIDATION_TIME_LIMIT =  120000L

external interface ManualEntryTabProps : Props {
    var polyglot: Polyglot
}

class ManualEntryTabState : State {
    var displayingError: Boolean = false
    var errorMessage: String = ""
}

class ManualEntryTab : RComponent<ManualEntryTabProps, ManualEntryTabState>() {
    init {
        state = ManualEntryTabState()
    }

    override fun RBuilder.render() {
        context.ValidationContext.Consumer { validationContext ->
            LocalizationContext.Consumer { localizationContext ->
                val language = localizationContext?.selectedLanguage ?: Language.ENGLISH

                styledDiv {
                    css {
                        +ManualEntryTabStyle.mainContainer
                    }
                    heading {
                        text = props.polyglot.t("manual_entry_title")
                    }

                    manualEntryTextArea {
                        currentText = validationContext?.currentManualEntryText ?: ""
                        placeholderText = props.polyglot.t("manual_entry_place_holder")
                        onTextUpdate = { str ->
                            validationContext?.updateManualEntryText?.invoke(str)
                            if (state.displayingError) {
                                setState {
                                    displayingError = false
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
                                val currentText = validationContext?.currentManualEntryText ?: ""
                                if (currentText.isNotEmpty()) {
                                    validateEnteredText(
                                        currentText,
                                        validationContext?.validationContext
                                            ?: ValidationContext().setBaseEngine("DEFAULT"),
                                        validationContext?.sessionId ?: "",
                                        validationContext?.presets ?: emptyList(),
                                        { id -> validationContext?.setSessionId?.invoke(id) },
                                        { outcome -> validationContext?.setManualValidationOutcome?.invoke(outcome) },
                                        { inProgress -> validationContext?.toggleManualValidationInProgress?.invoke(inProgress) }
                                    )
                                } else {
                                    val newErrorMessage = props.polyglot.t("manual_entry_empty_request_error")
                                    setState {
                                        errorMessage = newErrorMessage
                                        displayingError = true
                                    }
                                }
                            }
                            workInProgress = validationContext?.manualValidatingInProgress ?: false
                        }
                        styledDiv {
                            css {
                                +ManualEntryTabStyle.buttonBarDivider
                            }
                        }
                        presetSelect {
                            this.validationContext = validationContext?.validationContext
                                ?: ValidationContext().setBaseEngine("DEFAULT")
                            updateValidationContext = { ctx, resetBaseEngine ->
                                validationContext?.updateValidationContext?.invoke(ctx, resetBaseEngine)
                            }
                            updateIgPackageInfoSet = { set, resetBaseEngine ->
                                validationContext?.updateIgPackageInfoSet?.invoke(set, resetBaseEngine)
                            }
                            updateExtensionSet = { set, resetBaseEngine ->
                                validationContext?.updateExtensionSet?.invoke(set, resetBaseEngine)
                            }
                            updateProfileSet = { set, resetBaseEngine ->
                                validationContext?.updateProfileSet?.invoke(set, resetBaseEngine)
                            }
                            updateBundleValidationRuleSet = { set, resetBaseEngine ->
                                validationContext?.updateBundleValidationRuleSet?.invoke(set, resetBaseEngine)
                            }
                            setSessionId = { id ->
                                validationContext?.setSessionId?.invoke(id)
                            }
                            this.language = language
                            polyglot = props.polyglot
                            presets = validationContext?.presets ?: emptyList()
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
                    validationContext?.manualValidationOutcome?.let {
                        styledDiv {
                            css {
                                +ManualEntryTabStyle.resultsContainer
                            }
                            validationOutcomeContainer {
                                polyglot = props.polyglot
                                validationOutcome = it
                                inPage = true
                            }
                        }
                    }
                }
            }
        }
    }

    private fun validateEnteredText(
        fileContent: String,
        validationContext: ValidationContext,
        sessionId: String,
        presets: List<Preset>,
        setSessionId: (String) -> Unit,
        setValidationOutcome: (ValidationOutcome) -> Unit,
        toggleValidationInProgress: (Boolean) -> Unit
    ) {
        console.info("Attempting to validate with: " + validationContext.getBaseEngine())
        val localizedValidationContext: ValidationContext = Preset.getLocalizedValidationContextFromPresets(validationContext, presets) ?: return

        toggleValidationInProgress(true)
        console.info("validationContext :: sv == ${localizedValidationContext.getSv()}, version == ${validationContext.getTargetVer()}, languageCode == ${validationContext.getLanguageCode()}")
        val request = assembleRequest(
            validationContext = localizedValidationContext,
            fileName = generateFileName(fileContent),
            fileContent = fileContent,
            fileType = null
        ).setSessionId(sessionId)
        mainScope.launch {
            try {
                withTimeout(VALIDATION_TIME_LIMIT) {
                    val validationResponse = sendValidationRequest(request)
                    setSessionId(validationResponse.getSessionId())
                    val returnedOutcome = validationResponse.getOutcomes().map { it.setValidated(true) }
                    console.info("File validated\n"
                            + "filename -> " + returnedOutcome.first().getFileInfo().fileName
                            + "content -> " + returnedOutcome.first().getFileInfo().fileContent
                            + "type -> " + returnedOutcome.first().getFileInfo().fileType
                            + "Issues ::\n" + returnedOutcome.first().getMessages()
                        .joinToString { "\n" })
                    setValidationOutcome(returnedOutcome.first())
                    toggleValidationInProgress(false)
                }
            } catch (e: TimeoutCancellationException) {
                setState {
                    errorMessage = props.polyglot.t("manual_entry_timeout_exception")
                    displayingError = true
                }
                toggleValidationInProgress(false)
            } catch (e: ValidationResponseException) {
                setState {
                    errorMessage = props.polyglot.t(
                        "manual_entry_validation_response_exception",
                        getJS(arrayOf(Pair("httpResponseCode", e.httpStatusCode)))
                    )
                    displayingError = true
                }
                println("Exception ${e.message}")
            } catch (e: Exception) {
                setState {
                    errorMessage = props.polyglot.t("manual_entry_cannot_parse_exception")
                    displayingError = true
                }
                println("Exception ${e.message}")
            } finally {
                toggleValidationInProgress(false)
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
    val resultsContainer by css {
        display = Display.flex
        flexDirection = FlexDirection.column
        minHeight = 600.px
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