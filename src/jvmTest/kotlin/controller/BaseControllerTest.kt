package controller

import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module

abstract class BaseControllerTest {

    init {
        stopKoin()
    }

    open fun before() {

    }

    fun startInjection(module: Module) {
        startKoin {
            modules(
                module
            )
        }
    }
}