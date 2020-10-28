package desktop

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import stopServer
import tornadofx.App
import tornadofx.View
import tornadofx.webview

/*
 * Initial dimensions of the 'application' window we spawn to contain the webview.
 */
private const val PREF_WIDTH = 1200.0
private const val PREF_HEIGHT = 1000.0

/**
 * Instead of maintaining code for three separate desktop applications (OSX, Linux, Windows), we take the existing
 * KotlinJS front end, and use the TornadoFX library `(https://tornadofx.io/)` to wrap it in a Chromium powered webview.
 * This chromium app then communicates with a locally hosted instance of the Ktor backend, giving the 'illusion' of a
 * local desktop application.
 *
 * To use TornadoFX, our desktop.CliApp must extend the App class in TornadoFx.
 */
class CliApp: App(ApplicationView::class) {
    /**
     * On close, we need to shutdown the Ktor backend server as well. We do this by overriding the stop method, then
     * calling the `stopServer()` we defined.
     */
    override fun stop() {
        super.stop()
        stopServer()
    }
}

/**
 * Our main desktop.ApplicationView class creates a webView object and loads the address of the server.
 */
class ApplicationView: View() {
    override val root = webview()

    init {
        with(root) {
            setPrefSize(PREF_WIDTH, PREF_HEIGHT)
            // TODO fix this so it's not hardcoded
            engine.load("http://localhost:8080/")
        }
    }
}

/**
 * Method to launch desktop application within it's own coroutine.
 */
fun launchLocalApp() {
    GlobalScope.launch {
        tornadofx.launch<CliApp>()
    }
}