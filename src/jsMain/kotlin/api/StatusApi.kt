package api

import constants.PACKAGES_SERVER_STATUS_ENDPOINT
import constants.TX_SERVER_STATUS_ENDPOINT
import constants.VALIDATION_ENGINES_ENDPOINT
import constants.VALIDATION_PRESETS_ENDPOINT
import io.ktor.client.call.*
import io.ktor.client.request.*
import model.Preset

suspend fun isTerminologyServerUp(): Boolean {
    return jsonClient.get(urlString = endpoint + TX_SERVER_STATUS_ENDPOINT).body()
}

suspend fun getValidationPresets(): List<Preset> {
    return jsonClient.get(urlString = endpoint + VALIDATION_PRESETS_ENDPOINT).body()
}

suspend fun isPackagesServerUp(): Boolean {
    return jsonClient.get(urlString = endpoint + PACKAGES_SERVER_STATUS_ENDPOINT).body()
}

