package reactredux.reducers

import model.ValidationOutcome
import reactredux.actions.AddValidationOutcome
import reactredux.actions.ClearValidationOutcomes
import reactredux.actions.RemoveFile
import reactredux.actions.UploadFile
import redux.RAction

fun uploadedFiles(state: MutableList<ValidationOutcome> = mutableListOf(), action: RAction): MutableList<ValidationOutcome> =
    when (action) {
        is UploadFile -> {
            state.add(ValidationOutcome().setFileInfo(action.fileInfo))
            state
        }
        is RemoveFile -> {
            state.filter {
                it.getFileInfo() != action.fileInfo
            }.toMutableList()
        }
        is AddValidationOutcome -> {
            state.map {
                if (it.getFileInfo() == action.outcome.getFileInfo()) {
                    action.outcome
                } else {
                    it
                }
            }.toMutableList()
        }
        is ClearValidationOutcomes -> {
            mutableListOf()
        }
        else -> state
    }