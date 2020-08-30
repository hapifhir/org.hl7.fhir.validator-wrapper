package reactredux.containers

import model.FileInfo
import model.ValidationOutcome
import org.w3c.files.FileList
import react.RClass
import react.RProps
import react.invoke
import react.redux.rConnect
import reactredux.actions.RemoveFile
import reactredux.state.AppState
import redux.WrapperAction
import ui.components.UploadFilesButton
import ui.components.UploadFilesButtonProps

private interface UploadedFileListStateProps : RProps {
    var uploadedFiles: MutableList<ValidationOutcome>
}

private interface UploadedFileListDispatchProps : RProps {
    var removeFile: (FileInfo) -> Unit
}

val uploadFilesList: RClass<RProps> =
    rConnect<AppState, RemoveFile, WrapperAction, RProps, UploadedFileListStateProps, UploadedFileListDispatchProps, UploadFilesButtonProps>(
        { state, _ ->
            uploadedFiles = state.uploadedFiles
        },
        { dispatch, _ ->
            removeFile = { dispatch(RemoveFile(it)) }
        }
    )(FileList::class.js.unsafeCast<RClass<UploadFilesButtonProps>>())