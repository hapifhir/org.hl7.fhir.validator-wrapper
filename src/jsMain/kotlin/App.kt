import api.sendValidationRequest
import css.LandingPageStyle
import css.TextStyle
import css.const.PADDING_L
import css.const.PADDING_XXL
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.css.TextAlign
import kotlinx.css.marginBottom
import kotlinx.css.marginTop
import kotlinx.css.textAlign
import model.CliContext
import model.ValidationOutcome
import react.*
import styled.css
import styled.styledDiv
import styled.styledH1
import ui.components.header
import ui.components.tabLayout
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
            styledDiv {
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
                            println("Validation result for: ${vo.getFileInfo().fileName}")
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


