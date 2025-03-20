package reactredux.store

import reactredux.slices.*

data class AppState(
    val localizationSlice: LocalizationSlice.State = LocalizationSlice.State(),
    val validationSessionSlice: ValidationSessionSlice.State = ValidationSessionSlice.State(),
    val manualEntrySlice: ManualEntrySlice.State = ManualEntrySlice.State(),
    val uploadedResourceSlice: UploadedResourceSlice.State = UploadedResourceSlice.State(),
    val validationContextSlice: ValidationContextSlice.State = ValidationContextSlice.State(),
    val appScreenSlice: AppScreenSlice.State = AppScreenSlice.State(),
    val presetsSlice: PresetsSlice.State = PresetsSlice.State()
)
