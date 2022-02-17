@file:JsModule("react-ace")
@file:JsNonModule

import kotlinext.js.Object
import org.w3c.dom.Element
import react.*

@JsName("default")
external val aceEditor: Component<AceEditorProps, RState>

external interface AceEditorProps : RProps {
    var ref : RMutableRef<Nothing>
    var mode: String
    var theme : String
    var annotations : Array<Annotation>
    var markers : Array<Marker>
    var defaultValue : String?
    var value : String?
    var setOptions : AceOptions
}

