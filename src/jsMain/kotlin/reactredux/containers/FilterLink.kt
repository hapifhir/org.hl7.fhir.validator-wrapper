package reactredux.containers

import reactredux.actions.SetVisibilityFilter
import reactredux.components.Link
import reactredux.components.LinkProps
import reactredux.enums.VisibilityFilter
import reactredux.reducers.State
import react.ComponentClass
import react.Props
import react.invoke
import react.redux.rConnect
import redux.WrapperAction

external interface FilterLinkProps : Props {
    var filter: VisibilityFilter
}

private external interface LinkStateProps : Props {
    var active: Boolean
}

private external interface LinkDispatchProps : Props {
    var onClick: () -> Unit
}

val filterLink: ComponentClass<FilterLinkProps> =
    rConnect<State, SetVisibilityFilter, WrapperAction, FilterLinkProps, LinkStateProps, LinkDispatchProps, LinkProps>(
        { state, ownProps ->
            active = state.visibilityFilter == ownProps.filter
        },
        { dispatch, ownProps ->
            onClick = { dispatch(SetVisibilityFilter(ownProps.filter)) }
        }
    )(Link::class.js.unsafeCast<ComponentClass<LinkProps>>())
