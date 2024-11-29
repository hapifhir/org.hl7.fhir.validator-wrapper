package reactredux.store

import redux.RAction
import redux.rEnhancer

/**
 * And the store is created with the createStore function, note that the last argument of the compose function enables
 * Redux dev tools.
 */
val myStore = createStore<AppState, RAction, dynamic>(
    combinedReducers(),
    AppState(),
    compose(
        rThunk(),
        rEnhancer(),
        rThunk(),
        js(code = "window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__()")
    )
)

