package reactredux.store

import reactredux.slices.*
import redux.*
import kotlin.reflect.KProperty1

/**
 * Reducers are combined using this function
 */
fun combinedReducers() = combineReducersInferred(
    mapOf(
        AppState::localizationSlice to LocalizationSlice::reducer,
        AppState::validationSessionSlice to ValidationSessionSlice::reducer,
        AppState::manuallyEnteredResourceSlice to ManuallyEnteredResourceSlice::reducer,
        AppState::uploadedResourceSlice to UploadedResourceSlice::reducer,
        AppState::validationContextSlice to ValidationContextSlice::reducer,
        AppState::appScreenSlice to AppScreenSlice::reducer
    )
)

fun <S, A, R> combineReducersInferred(reducers: Map<KProperty1<S, R>, Reducer<*, A>>): Reducer<S, A> {
    return combineReducers(reducers.mapKeys { it.key.name })
}