package reactredux.thunk

import reactredux.store.AppState
import reactredux.store.RThunk
import reactredux.store.nullAction
import redux.RAction
import redux.WrapperAction

class FetchLocalizedStringThunk : RThunk {
    override fun invoke(dispatch: (RAction) -> WrapperAction, getState: () -> AppState): WrapperAction {
        //dispatch(TaskListSlice.SetIsLoading(true))
//        window.setTimeout({
//
//            val tasks = listOf(
//                Task("Kotlin"),
//                Task("is"),
//                Task("awesome")
//            )
//            dispatch(LocalizationSlice.State().polyglotInstance)
//            dispatch(TaskListSlice.SetIsLoading(false))
//        }, 2000)

        return nullAction
    }
}