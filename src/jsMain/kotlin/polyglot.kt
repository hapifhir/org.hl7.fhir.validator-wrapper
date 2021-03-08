@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

import kotlin.js.*
import org.khronos.webgl.*
import org.w3c.dom.*
import org.w3c.dom.events.*
import org.w3c.dom.parsing.*
import org.w3c.dom.svg.*
import org.w3c.dom.url.*
import org.w3c.fetch.*
import org.w3c.files.*
import org.w3c.notifications.*
import org.w3c.performance.*
import org.w3c.workers.*
import org.w3c.xhr.*
import Polyglot.PolyglotOptions
import Polyglot.InterpolationOptions
import Polyglot.`T$1`
import Polyglot.`T$2`
import Polyglot.InterpolationTokenOptions
import Polyglot.PluralRules

@JsModule("node-polyglot")
@JsNonModule
open external class Polyglot(options: PolyglotOptions = definedExternally) {
    open fun extend(phrases: Any, prefix: String = definedExternally)
    open fun t(phrase: String, options: Number = definedExternally): String
    open fun clear()
    open fun replace(phrases: Any)
    open fun locale(locale: String = definedExternally): String
    open fun has(phrase: String): Boolean
    open fun unset(phrases: Any, prefix: String = definedExternally)
    interface `T$0` {
        var length: Number
    }
    interface InterpolationOptions {
        var smart_count: dynamic /* Number? | `T$0`? */
            get() = definedExternally
            set(value) = definedExternally
        var `_`: String?
            get() = definedExternally
            set(value) = definedExternally
        operator fun get(interpolationKey: String): Any?
        operator fun set(interpolationKey: String, value: Any)
    }
    interface InterpolationTokenOptions {
        var prefix: String?
            get() = definedExternally
            set(value) = definedExternally
        var suffix: String?
            get() = definedExternally
            set(value) = definedExternally
    }
    interface `T$1` {
        operator fun get(lang: String): ((n: Number) -> Number)?
        operator fun set(lang: String, value: (n: Number) -> Number)
    }
    interface `T$2` {
        operator fun get(lang: String): Array<String>?
        operator fun set(lang: String, value: Array<String>)
    }
    interface PluralRules {
        var pluralTypes: `T$1`
        var pluralTypeToLanguages: `T$2`
    }
    interface PolyglotOptions {
        var phrases: Any?
            get() = definedExternally
            set(value) = definedExternally
        var locale: String?
            get() = definedExternally
            set(value) = definedExternally
        var allowMissing: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var onMissingKey: ((key: String, options: InterpolationOptions, locale: String) -> String)?
            get() = definedExternally
            set(value) = definedExternally
        var warn: ((message: String) -> Unit)?
            get() = definedExternally
            set(value) = definedExternally
        var interpolation: InterpolationTokenOptions?
            get() = definedExternally
            set(value) = definedExternally
        var pluralRules: PluralRules?
            get() = definedExternally
            set(value) = definedExternally
    }

    companion object {
        fun transformPhrase(phrase: String, options: Number = definedExternally, locale: String = definedExternally): String
        fun transformPhrase(phrase: String, options: InterpolationOptions = definedExternally, locale: String = definedExternally): String
    }
}