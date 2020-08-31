package reactredux.reducers

import reactredux.actions.ToggleUploadInProgress
import redux.RAction

fun uploadInProgress(state: Boolean = false, action: RAction): Boolean =
    when (action) {
        is ToggleUploadInProgress -> {
            action.uploading
        }
        else -> state
    }