package reactredux.containers

import model.CliContext
import model.ValidationOutcome
import react.RClass
import react.RProps
import react.invoke
import react.redux.rConnect
import reactredux.actions.AddManuallyEnteredFileValidationOutcome
import reactredux.state.AppState
import redux.RAction
import redux.WrapperAction
import ui.components.ResourceEntryFieldComponent
import ui.components.ResourceEntryFieldProps

private interface ResourceEntryFieldStateProps : RProps {
    var cliContext: CliContext
}

private interface ResourceEntryFieldDispatchProps : RProps {
    var addManuallyEnteredFileValidationOutcome: (ValidationOutcome) -> Unit
}

val resourceEntryField: RClass<RProps> =
    rConnect<AppState, RAction, WrapperAction, RProps, ResourceEntryFieldStateProps, ResourceEntryFieldDispatchProps, ResourceEntryFieldProps>(
        { state, _ ->
            cliContext = state.cliContext
        },
        { dispatch, _ ->
            addManuallyEnteredFileValidationOutcome = { dispatch(AddManuallyEnteredFileValidationOutcome(it)) }
        }
    )(ResourceEntryFieldComponent::class.js.unsafeCast<RClass<ResourceEntryFieldProps>>())