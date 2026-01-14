package context

import model.AppScreen
import react.*

external interface AppScreenContextValue {
    var appScreen: AppScreen
    var setAppScreen: (AppScreen) -> Unit
}

val AppScreenContext = createContext<AppScreenContextValue>()

external interface AppScreenProviderProps : PropsWithChildren

class AppScreenProviderState : State {
    var appScreen: AppScreen = AppScreen.VALIDATOR
}

class AppScreenProvider : RComponent<AppScreenProviderProps, AppScreenProviderState>() {

    init {
        state = AppScreenProviderState()
    }

    override fun RBuilder.render() {
        val setAppScreen: (AppScreen) -> Unit = { screen ->
            setState {
                appScreen = screen
            }
        }

        val contextValue = js("{}")
            .unsafeCast<AppScreenContextValue>()
            .apply {
                this.appScreen = state.appScreen
                this.setAppScreen = setAppScreen
            }

        AppScreenContext.Provider {
            attrs.value = contextValue
            props.children?.let { +it }
        }
    }
}

fun RBuilder.appScreenProvider(handler: AppScreenProviderProps.() -> Unit) {
    return child(AppScreenProvider::class) {
        this.attrs(handler)
    }
}
