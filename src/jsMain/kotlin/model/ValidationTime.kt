package model

import kotlinx.serialization.Serializable

@Serializable
actual class ValidationTime actual constructor() {

    private var txTime : Long = 0
    private var sdTime : Long = 0
    private var loadTime : Long = 0
    private var fhirPath : Long = 0
    private var specTime : Long = 0
    private var overall : Long = 0

    actual fun getTxTime(): Long {
        return txTime;
    }

    actual fun setTxTime(txTime: Long): ValidationTime {
       this.txTime = txTime
        return this
    }

    actual fun getSdTime(): Long {
        return sdTime
    }

    actual fun setSdTime(sdTime: Long): ValidationTime {
        this.sdTime = sdTime
        return this
    }

    actual fun getLoadTime(): Long {
        return this.loadTime
    }

    actual fun setLoadTime(loadTime: Long): ValidationTime {
        this.loadTime = loadTime
        return this
    }

    actual fun getFhirPath(): Long {
       return this.fhirPath
    }

    actual fun setFhirPath(fhirPath: Long): ValidationTime {
       this.fhirPath = fhirPath
        return this
    }

    actual fun getSpecTime(): Long {
       return this.specTime
    }

    actual fun setSpecTime(specTime: Long): ValidationTime {
       this.specTime = specTime
        return this
    }

    actual fun getOverall(): Long {
       return this.overall
    }

    actual fun setOverall(overall: Long): ValidationTime {
        this.overall = overall
        console.log("Overall time:" + overall)
        return this
    }
}