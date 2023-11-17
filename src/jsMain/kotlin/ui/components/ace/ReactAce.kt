@file:JsModule("react-ace")
@file:JsNonModule

package ui.components.ace

import react.*

@JsName("default")
external val aceEditor: ComponentClass<AceEditorProps>

    external interface AceEditorProps : Props {
    var ref : RefObject<Nothing>
    var mode: String
    var theme : String
    var height : String?
    var width : String?

    var showPrintMargin: Boolean?
    var readOnly: Boolean?

    var annotations : Array<AceAnnotation>
    var markers : Array<AceMarker>
    var defaultValue : String?
    var value : String?
    var setOptions : AceOptions

    var onCursorChange : () -> Unit
}

