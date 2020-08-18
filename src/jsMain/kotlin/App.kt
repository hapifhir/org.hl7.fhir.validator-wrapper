import api.sendValidationRequest
import constants.FhirFormat
import css.FileListStyle
import css.LandingPageStyle
import css.TextStyle
import css.const.PADDING_L
import css.const.PADDING_XXL
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.css.*
import model.CliContext
import model.FileInfo
import model.ValidationOutcome
import react.*
import react.dom.div
import react.dom.h1
import react.dom.h2
import react.dom.ul
import styled.*
import uicomponents.*
import utils.assembleRequest

external interface AppState : RState {
    var validationOutcome: ValidationOutcome
    var cliContext: CliContext
}

val mainScope = MainScope()

class App : RComponent<RProps, AppState>() {
    override fun AppState.init() {
        // Inject global styles
//        StyledComponents.injectGlobal(styles.toString())

        // For testing
        validationOutcome = ValidationOutcome().setIssues(listOf())

        // Initialize CLI Context
        var context = CliContext()

        mainScope.launch {
            setState {
                // Set CLI Context
                cliContext = context
            }
        }
    }

    override fun RBuilder.render() {

        styledDiv {
            css {
                +LandingPageStyle.mainDiv
            }
            header { }
            styledP {
                css {
                    textAlign = TextAlign.center
                    marginTop = PADDING_L
                    marginBottom = PADDING_XXL
                }
                styledH1 {
                    css {
                        +TextStyle.h1
                    }
                    +"FHIR Validator"
                }
            }
            tabLayout {
                cliContext = state.cliContext
                onValidate = { it ->
                    val request = assembleRequest(state.cliContext, it)
                    mainScope.launch {
                        val returnedOutcome = sendValidationRequest(request)
                        returnedOutcome.forEach { vo ->
                            println("Validation for: ${vo.getFileInfo().fileName}")
                            vo.getIssues().forEach { vi ->
                                println("${vi.getSeverity()} :: ${vi.getDetails()}")
                            }
                        }
                    }
                }
            }
        }
    }
}


