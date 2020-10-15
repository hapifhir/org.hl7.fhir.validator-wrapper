package reactredux.reducers

import model.ValidationOutcome
import reactredux.actions.*
import redux.RAction

fun uploadedFiles(state: List<ValidationOutcome> = emptyList(), action: RAction): List<ValidationOutcome> =
    when (action) {
        is UploadFile -> {
            println("Adding file ${action.fileInfo.fileName} to state list")
            state + ValidationOutcome().setFileInfo(action.fileInfo)
        }
        is RemoveFile -> {
            state.filter {
                it.getFileInfo() != action.fileInfo
            }.toList()
        }
        is AddValidationOutcome -> {
            state.map {
                if (it.getFileInfo().fileName == action.outcome.getFileInfo().fileName) {
                    action.outcome.setValidated(true)
                } else {
                    it
                }
            }.toList()
        }
        is ToggleValidationInProgress -> {
            state.map {
                if (it.getFileInfo().fileName == action.fileInfo.fileName) {
                    it.setValidating(action.validating)
                } else {
                    it
                }
            }.toList()
        }
        is ClearValidationOutcomes -> {
            emptyList()
        }
        else -> {
            state
        }
    }