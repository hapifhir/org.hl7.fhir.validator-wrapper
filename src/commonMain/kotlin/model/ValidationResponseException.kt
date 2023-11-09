package model

class ValidationResponseException(val httpStatusCode: Int, message: String) : Exception(message) {
}