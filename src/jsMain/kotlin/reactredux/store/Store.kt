package reactredux.store

import reactredux.state.AppState
import reactredux.state.rootReducer
import redux.createStore
import redux.rEnhancer

val store = createStore(
        ::rootReducer,
        AppState(),
        rEnhancer()
)
