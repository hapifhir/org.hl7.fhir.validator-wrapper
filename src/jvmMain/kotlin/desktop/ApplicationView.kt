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
 * To display the webview natively as an application on desktops, we use the TornadoFX library (https://tornadofx.io/).
 * As a result, our desktop.CliApp mus extend the App class in TornadoFx.
 */
class CliApp: App(ApplicationView::class) {
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

//TODO shut down server on window close