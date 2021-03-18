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
import ui.components.tabs.uploadtab.FileUploadTab

private interface FileUploadTabProps : RProps {
    var uploadedFiles: List<ValidationOutcome>
}

private interface FileUploadTabDispatchProps : RProps {
    var deleteFile: (FileInfo) -> Unit
}

val uploadFilesList: RClass<RProps> =
    rConnect<AppState, RAction, WrapperAction, RProps, FileUploadTabProps, FileUploadTabDispatchProps, FileUploadTabProps>(
        { state, _ ->
            uploadedFiles = state.uploadedResourceSlice.uploadedFiles
        },
        { dispatch, _ ->
            deleteFile = { dispatch(UploadedResourceSlice.RemoveFile(it)) }
        }
    )(FileUploadTab::class.js.unsafeCast<RClass<FileUploadTabProps>>())