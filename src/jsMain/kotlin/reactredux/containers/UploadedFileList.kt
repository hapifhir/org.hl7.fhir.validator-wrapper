package reactredux.containers

import model.FileInfo
import model.ValidationOutcome
import react.RClass
import react.RProps
import react.invoke
import react.redux.rConnect
import reactredux.slices.UploadedResourceSlice
import reactredux.store.AppState
import redux.RAction
import redux.WrapperAction
import ui.components.FileListComponent
import ui.components.FileListProps

private interface UploadedFileListStateProps : RProps {
    var uploadedFiles: List<ValidationOutcome>
}

private interface UploadedFileListDispatchProps : RProps {
    var removeFile: (FileInfo) -> Unit
}

val uploadFilesList: RClass<RProps> =
    rConnect<AppState, RAction, WrapperAction, RProps, UploadedFileListStateProps, UploadedFileListDispatchProps, FileListProps>(
        { state, _ ->
            uploadedFiles = state.uploadedResourceSlice.uploadedFiles
        },
        { dispatch, _ ->
            removeFile = { dispatch(UploadedResourceSlice.RemoveFile(it)) }
        }
    )(FileListComponent::class.js.unsafeCast<RClass<FileListProps>>())