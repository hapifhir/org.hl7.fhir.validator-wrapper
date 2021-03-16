package reactredux.store

import reactredux.slices.*

data class AppState(
    val localizationSlice: LocalizationSlice.State = LocalizationSlice.State(),
    val validationSessionSlice: ValidationSessionSlice.State = ValidationSessionSlice.State(),
    val manuallyEnteredResourceSlice: ManuallyEnteredResourceSlice.State = ManuallyEnteredResourceSlice.State(),
    val uploadedResourceSlice: UploadedResourceSlice.State = UploadedResourceSlice.State(),
    val validationContextSlice: ValidationContextSlice.State = ValidationContextSlice.State(),
    val appScreenSlice: AppScreenSlice.State = AppScreenSlice.State()
    )
