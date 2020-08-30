package reactredux.reducers

import model.ValidationOutcome
import reactredux.actions.SetManuallyEnteredFile
import reactredux.actions.UpdateManuallyEnteredFile
import redux.RAction

fun manuallyEnteredFile(state: ValidationOutcome = ValidationOutcome(), action: RAction): ValidationOutcome =
    when (action) {
        is SetManuallyEnteredFile -> {
            ValidationOutcome().setFileInfo(action.fileInfo)
        }
        is UpdateManuallyEnteredFile -> {
            action.validationOutcome
        }
        else -> state
    }