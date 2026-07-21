package utils

import org.hl7.fhir.validation.packages.PackageCacheDownloader

class PackageCacheDownloaderRunnable : Runnable {
    override fun run() {
        logger.info("PackageCacheDownloader: starting pre-load of all available packages")
        val packageCacheDownloader = PackageCacheDownloader()
        packageCacheDownloader.visitPackages()
        logger.info("PackageCacheDownloader: pre-load complete.")
    }
}