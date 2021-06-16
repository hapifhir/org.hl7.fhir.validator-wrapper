package api

import constants.PACKAGES_SERVER_STATUS_ENDPOINT
import constants.TX_SERVER_STATUS_ENDPOINT
import io.ktor.client.request.*

suspend fun isTerminologyServerUp(): Boolean {
    return jsonClient.get(urlString = endpoint + TX_SERVER_STATUS_ENDPOINT)
}

suspend fun isPackagesServerUp(): Boolean {
    return jsonClient.get(urlString = endpoint + PACKAGES_SERVER_STATUS_ENDPOINT)
}

