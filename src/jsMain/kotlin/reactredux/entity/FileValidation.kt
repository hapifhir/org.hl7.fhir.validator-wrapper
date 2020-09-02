package reactredux.entity

import model.ValidationOutcome

/**
 *
 */
data class FileValidation(
    var validationOutcome: ValidationOutcome = ValidationOutcome(),
    var validated: Boolean = false
)

