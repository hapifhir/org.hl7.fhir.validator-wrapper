package reactredux.slices

import model.FileInfo
import model.ValidationOutcome
import redux.RAction

object UploadedResourceSlice {

    data class State(
        val uploadInProgress: Boolean = false,
        val uploadedFiles: List<ValidationOutcome> = emptyList(),
    )

    data class UploadFile(val fileInfo: FileInfo) : RAction
    data class RemoveFile(val fileInfo: FileInfo) : RAction
    data class ToggleValidationInProgress(val validating: Boolean, val fileInfo: FileInfo) : RAction
    data class AddValidationOutcome(val outcome: ValidationOutcome) : RAction
    class ClearValidationOutcomes : RAction

    fun reducer(state: State = State(), action: RAction): State {
        return when (action) {
            is UploadFile -> state.copy(uploadedFiles = state.uploadedFiles + ValidationOutcome().setFileInfo(action.fileInfo))
            is RemoveFile -> state.copy(uploadedFiles = state.uploadedFiles.filter { it.getFileInfo() != action.fileInfo }
                .toList())
            is AddValidationOutcome -> state.copy(uploadedFiles = state.uploadedFiles.map {
                if (it.getFileInfo().fileName == action.outcome.getFileInfo().fileName) {
                    action.outcome.setValidated(true)
                } else {
                    it
                }
            }.toList())
            is ToggleValidationInProgress -> state.copy(uploadedFiles = state.uploadedFiles.map {
                if (it.getFileInfo().fileName == action.fileInfo.fileName) {
                    it.setValidating(action.validating)
                } else {
                    it
                }
            }.toList())
            is ClearValidationOutcomes -> state.copy(uploadedFiles = emptyList())
            else -> {
                state
            }
        }
    }
}