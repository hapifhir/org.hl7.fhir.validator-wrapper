package reactredux.actions

import model.AppScreen
import model.CliContext
import model.FileInfo
import model.ValidationOutcome
import redux.RAction

class UploadFile(val fileInfo: FileInfo) : RAction
class RemoveFile(val fileInfo: FileInfo) : RAction
class AddValidationOutcome(val outcome: ValidationOutcome) : RAction
class ClearValidationOutcomes() : RAction
class ToggleValidationInProgress(val validating: Boolean, val fileInfo: FileInfo) : RAction
class SetScreen(val appScreen: AppScreen) : RAction

class AddManuallyEnteredFileValidationOutcome(val validationOutcome: ValidationOutcome) : RAction
class ToggleManuallyEnteredValidationInProgress(val validationInProgress: Boolean) : RAction

class UpdateContext(val context: CliContext) : RAction

class ToggleValidating(val validating: Boolean) : RAction




