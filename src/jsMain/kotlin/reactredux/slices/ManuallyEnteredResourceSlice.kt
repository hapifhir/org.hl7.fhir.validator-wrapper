package reactredux.slices

import model.ValidationOutcome
import redux.RAction

object ManuallyEnteredResourceSlice {

    data class State(
        val manuallyEnteredFileData: ValidationOutcome = ValidationOutcome(),
        val validationInProgress: Boolean = false,
    )

    data class AddManuallyEnteredFileValidationOutcome(val validationOutcome: ValidationOutcome) : RAction
    data class ToggleValidationInProgress(val validationInProgress: Boolean) : RAction

    fun reducer(state: State = State(), action: RAction): State {
        return when (action) {
            is AddManuallyEnteredFileValidationOutcome -> state.copy(manuallyEnteredFileData = action.validationOutcome)
            is ToggleValidationInProgress -> state.copy(validationInProgress = action.validationInProgress)
            else -> state
        }
    }
}