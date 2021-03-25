@file:JsModule("redux")
@file:JsNonModule

package reactredux.store

import redux.*

//This wrapper is currently necessary to avoid this error
//"When accessing module declarations from UMD, they must be marked by both @JsModule and @JsNonModule"

external fun <S, A, R> createStore(
    reducer: Reducer<S, A>,
    preloadedState: S,
    enhancer: Enhancer<S, Action, Action, A, R>,
): Store<S, A, R>

external fun <A, T1, T2, R> compose(function1: (T2) -> R, function2: (T1) -> T2, function3: (A) -> T1): (A) -> R

external fun <S, A1, R1, A2, R2> applyMiddleware(
    vararg middlewares: Middleware<S, A1, R1, A2, R2>,
): Enhancer<S, A1, R1, A2, R2>
