package reactredux.store

import reactredux.slices.*

data class AppState(
    val localizationState: LocalizationSlice.State = LocalizationSlice.State(),
    val validationSessionState: ValidationSlice.State = ValidationSlice.State(),
    val manuallyEnteredResourceState: ManuallyEnteredResourceSlice.State = ManuallyEnteredResourceSlice.State(),
    val uploadedResourceSlice: UploadedResourceSlice.State = UploadedResourceSlice.State(),
    val validationContextSlice: ValidationContextSlice.State = ValidationContextSlice.State(),
    val appScreenSlice: AppScreenSlice.State = AppScreenSlice.State()
    )
