package reactredux.state

import model.ValidationOutcome
import reactredux.reducers.manuallyEnteredFile
import reactredux.reducers.uploadInProgress
import reactredux.reducers.uploadedFiles
import reactredux.reducers.validationInProgress
import redux.RAction

data class AppState(
    val uploadedFiles: MutableList<ValidationOutcome> = mutableListOf(),
    val manuallyEnteredFile: ValidationOutcome = ValidationOutcome(),
    val uploadInProgress: Boolean = false,
    val validationInProgress: Boolean = false
)

fun rootReducer(state: AppState, action: RAction) = AppState(
    uploadedFiles = uploadedFiles(state.uploadedFiles, action),
    manuallyEnteredFile = manuallyEnteredFile(state.manuallyEnteredFile, action),
    uploadInProgress = uploadInProgress(state.uploadInProgress, action),
    validationInProgress = validationInProgress(state.validationInProgress, action)
)