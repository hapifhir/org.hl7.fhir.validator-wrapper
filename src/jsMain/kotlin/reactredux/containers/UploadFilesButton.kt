package reactredux.containers

import model.FileInfo
import react.RClass
import react.RProps
import react.invoke
import react.redux.rConnect
import reactredux.slices.UploadedResourceSlice
import reactredux.store.AppState
import redux.RAction
import redux.WrapperAction
import ui.components.UploadFilesButton
import ui.components.UploadFilesButtonProps

private interface UploadFilesButtonStateProps : RProps {}

private interface UploadFilesButtonDispatchProps : RProps {
    var uploadFile: (FileInfo) -> Unit
}

val uploadFilesButton: RClass<RProps> =
    rConnect<AppState, RAction, WrapperAction, RProps, UploadFilesButtonStateProps, UploadFilesButtonDispatchProps, UploadFilesButtonProps>(
        { _, _ ->
        },
        { dispatch, _ ->
            uploadFile = {
                dispatch(UploadedResourceSlice.UploadFile(it))
            }
        }
    )(UploadFilesButton::class.js.unsafeCast<RClass<UploadFilesButtonProps>>())