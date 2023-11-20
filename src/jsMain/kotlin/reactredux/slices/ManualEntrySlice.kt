package reactredux.slices

import model.ValidationOutcome
import model.ValidationTime
import redux.RAction

object ManualEntrySlice {

    data class State(
        var validationOutcome: ValidationOutcome? = null,
        var validationTime : ValidationTime? = null,
        var currentManuallyEnteredText: String = "",
        var validatingManualEntryInProgress: Boolean = false,
    )

    data class AddManualEntryOutcome(val validationOutcome: ValidationOutcome?) : RAction
    data class SetManualEntryValidationTime(val validationTime: ValidationTime?) : RAction
    data class ToggleValidationInProgress(val validationInProgress: Boolean) : RAction
    data class UpdateCurrentlyEnteredText(val enteredText: String) : RAction

    fun reducer(state: State = State(), action: RAction): State {
        return when (action) {
            is AddManualEntryOutcome -> state.copy(validationOutcome = action.validationOutcome)
            is SetManualEntryValidationTime -> state.copy(validationTime = action.validationTime)
            is ToggleValidationInProgress -> state.copy(validatingManualEntryInProgress = action.validationInProgress)
            is UpdateCurrentlyEnteredText -> state.copy(currentManuallyEnteredText = action.enteredText)
            else -> state
        }
    }
}