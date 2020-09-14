package reactredux.state

import model.AppScreen
import model.CliContext
import model.ValidationOutcome
import reactredux.reducers.*
import redux.RAction

data class AppState(
    val uploadedFiles: List<ValidationOutcome> = emptyList(),
    val manuallyEnteredFile: ValidationOutcome = ValidationOutcome(),
    val uploadInProgress: Boolean = false,
    val validationInProgress: Boolean = false,
    val cliContext: CliContext = CliContext(),
    val appScreen: AppScreen = AppScreen.VALIDATOR
)

fun rootReducer(state: AppState, action: RAction) = AppState(
    uploadedFiles = uploadedFiles(state.uploadedFiles, action),
    manuallyEnteredFile = manuallyEnteredFile(state.manuallyEnteredFile, action),
    validationInProgress = validationInProgress(state.validationInProgress, action),
    cliContext = cliContext(state.cliContext, action),
    appScreen = appScreen(state.appScreen, action)
)