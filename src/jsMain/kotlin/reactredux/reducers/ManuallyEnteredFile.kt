package reactredux.reducers

import model.ValidationOutcome
import reactredux.actions.AddManuallyEnteredFileValidationOutcome
import redux.RAction

fun manuallyEnteredFile(state: ValidationOutcome = ValidationOutcome(), action: RAction): ValidationOutcome =
    when (action) {
        is AddManuallyEnteredFileValidationOutcome -> {
            action.validationOutcome
        }
        else -> state
    }