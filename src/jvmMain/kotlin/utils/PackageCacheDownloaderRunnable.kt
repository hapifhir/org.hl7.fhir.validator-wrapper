package utils

import org.hl7.fhir.validation.packages.PackageCacheDownloader

class PackageCacheDownloaderRunnable : Runnable {
    public override fun run() {
        println("PackageCacheDownloader: starting pre-load of all available packages")
        val packageCacheDownloader = PackageCacheDownloader()
        packageCacheDownloader.visitPackages()
        println("PackageCacheDownloader: pre-load complete.")
    }
}