package reactredux.containers

import model.FileInfo
import react.RClass
import react.RProps
import react.invoke
import react.redux.rConnect
import reactredux.actions.UploadFile
import reactredux.state.AppState
import redux.WrapperAction
import ui.components.UploadFilesButton
import ui.components.UploadFilesButtonProps

private interface UploadFilesButtonStateProps : RProps { }

private interface UploadFilesButtonDispatchProps : RProps {
    var uploadFile: (FileInfo) -> Unit
}

val uploadFilesButton: RClass<RProps> =
    rConnect<AppState, UploadFile, WrapperAction, RProps, UploadFilesButtonStateProps, UploadFilesButtonDispatchProps, UploadFilesButtonProps>(
        { state, _ ->
        },
        { dispatch, _ ->
            uploadFile = { dispatch(UploadFile(it)) }
        }
    )(UploadFilesButton::class.js.unsafeCast<RClass<UploadFilesButtonProps>>())