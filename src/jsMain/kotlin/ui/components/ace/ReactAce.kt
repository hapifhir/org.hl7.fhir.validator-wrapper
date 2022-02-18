@file:JsModule("react-ace")
@file:JsNonModule

package ui.components.ace

import react.*

@JsName("default")
external val aceEditor: RClass<AceEditorProps>

    external interface AceEditorProps : RProps {
    var ref : RMutableRef<Nothing>
    var mode: String
    var theme : String
    var height : String?
    var width : String?
    var annotations : Array<Annotation>
    var markers : Array<AceMarker>
    var defaultValue : String?
    var value : String?
    var setOptions : AceOptions
}

