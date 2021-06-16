package controller.uptime

import model.CapabilityStatement
import kotlin.time.ExperimentalTime

interface UptimeController {
    suspend fun isTerminologyServerUp(): Boolean
    suspend fun isPackagesServerUp(): Boolean
}