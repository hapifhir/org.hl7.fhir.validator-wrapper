package entity

import model.ValidationOutcome

/**
 *
 */
data class UploadState(
    var validationOutcomes: MutableList<ValidationOutcome> = mutableListOf(),
    var uploading: Boolean = false
)

