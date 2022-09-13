package reactredux.util

import redux.Reducer
import redux.combineReducers
import kotlin.reflect.KProperty1


/**
 * Helper function that combines reducers using [combineReducers] where the keys in the map are
 * properties of the state object instead of strings with the name of the state's properties
 * this helper function has 2 advantages over the original:
 *
 * 1. It is less error-prone, when you change the name of the property of the state you must change the
 * corresponding key or you will get a compile error.
 * 2. The compiler is now able to infer the [S] type parameter which means it is no longer needed to provide the 2 type parameters explicitly.
 *
 * @param S state
 * @param A action
 * @param R state property type
 *
 * @param reducers map where the key is the state property and the value is the reducer for said property.
 *
 * @return the combined reducer.
 *
 */
fun <S, A, R> combineReducers(reducers: Map<KProperty1<S, R>, Reducer<*, A>>): Reducer<S, A> {
    return combineReducers(reducers.mapKeys { it.key.name })
}