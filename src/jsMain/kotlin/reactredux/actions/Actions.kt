package reactredux.actions

import model.CliContext
import model.FileInfo
import model.ValidationOutcome
import redux.RAction

class UploadFile(val fileInfo: FileInfo) : RAction
class RemoveFile(val fileInfo: FileInfo) : RAction
class AddValidationOutcome(val outcome: ValidationOutcome) : RAction
class ClearValidationOutcomes() : RAction
class ToggleUploadInProgress(val uploading: Boolean) : RAction

class AddManuallyEnteredFileValidationOutcome(val validationOutcome: ValidationOutcome) : RAction

class UpdateContext(val context: CliContext) : RAction

class ToggleValidating(val validating: Boolean) : RAction




