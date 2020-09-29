package reactredux.reducers

import model.ValidationOutcome
import reactredux.actions.AddManuallyEnteredFileValidationOutcome
import reactredux.actions.ToggleManuallyEnteredValidationInProgress
import reactredux.actions.ToggleValidationInProgress
import redux.RAction

fun manuallyEnteredFile(state: ValidationOutcome = ValidationOutcome(), action: RAction): ValidationOutcome =
    when (action) {
        is AddManuallyEnteredFileValidationOutcome -> {
            action.validationOutcome
        }
        is ToggleManuallyEnteredValidationInProgress -> {
            state.setValidating(action.validationInProgress)
        }
        else -> state
    }