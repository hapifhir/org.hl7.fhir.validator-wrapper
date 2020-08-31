package reactredux.reducers

import model.ValidationOutcome
import reactredux.actions.AddValidationOutcome
import reactredux.actions.ClearValidationOutcomes
import reactredux.actions.RemoveFile
import reactredux.actions.UploadFile
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
                if (it.getFileInfo() == action.outcome.getFileInfo()) {
                    action.outcome
                } else {
                    it
                }
            }.toList()
        }
        is ClearValidationOutcomes -> {
            emptyList()
        }
        else -> state
    }