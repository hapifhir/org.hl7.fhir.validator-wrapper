package reactredux.containers

import reactredux.actions.SetVisibilityFilter
import reactredux.components.Link
import reactredux.components.LinkProps
import reactredux.enums.VisibilityFilter
import reactredux.reducers.State
import react.RClass
import react.RProps
import react.invoke
import react.redux.rConnect
import redux.WrapperAction

external interface FilterLinkProps : RProps {
    var filter: VisibilityFilter
}

private external interface LinkStateProps : RProps {
    var active: Boolean
}

private external interface LinkDispatchProps : RProps {
    var onClick: () -> Unit
}

val filterLink: RClass<FilterLinkProps> =
    rConnect<State, SetVisibilityFilter, WrapperAction, FilterLinkProps, LinkStateProps, LinkDispatchProps, LinkProps>(
        { state, ownProps ->
            active = state.visibilityFilter == ownProps.filter
        },
        { dispatch, ownProps ->
            onClick = { dispatch(SetVisibilityFilter(ownProps.filter)) }
        }
    )(Link::class.js.unsafeCast<RClass<LinkProps>>())
