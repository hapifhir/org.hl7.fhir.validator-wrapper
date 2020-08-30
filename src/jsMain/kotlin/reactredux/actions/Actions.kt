package reactredux.actions

import model.FileInfo
import model.ValidationOutcome
import redux.RAction

class UploadFile(val fileInfo: FileInfo): RAction
class RemoveFile(val fileInfo: FileInfo): RAction
class AddValidationOutcome(val outcome: ValidationOutcome): RAction
class ClearValidationOutcomes(): RAction
class ToggleUploadInProgress(val uploading: Boolean): RAction

class SetManuallyEnteredFile(val fileInfo: FileInfo): RAction
class UpdateManuallyEnteredFile(val validationOutcome: ValidationOutcome): RAction

class ToggleValidating(val validating: Boolean): RAction


