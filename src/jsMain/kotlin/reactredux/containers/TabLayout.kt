package reactredux.containers

import Polyglot
import react.RClass
import react.RProps
import react.invoke
import react.redux.rConnect
import reactredux.store.AppState
import redux.RAction
import redux.WrapperAction
import ui.components.TabLayout
import ui.components.TabLayoutProps
import ui.components.header.Header

private interface TabLayoutStateProps : RProps {
    var polyglot: Polyglot
}

private interface TabLayoutDispatchProps : RProps {

}

val tabLayout: RClass<RProps> =
    rConnect<AppState, RAction, WrapperAction, RProps, TabLayoutStateProps, TabLayoutDispatchProps, TabLayoutProps>(
        { state, _ ->
            polyglot = state.localizationSlice.polyglotInstance
        },
        { dispatch, _ ->
        }
    )(TabLayout::class.js.unsafeCast<RClass<TabLayoutProps>>())