package ui.components.ace

import react.MutableRefObject
import react.RefObject


fun gotoLine(editorRef: RefObject<Nothing>, line: Int) {
    editorRef.asDynamic().current.editor.gotoLine(line)
}

fun scrollToLine(editorRef: RefObject<Nothing>, line: Int) {
    editorRef.asDynamic().current.editor.scrollToLine(line, true, true, null)
}

fun setAnnotations(editorRef: RefObject<Nothing>, aceAnnotations : Array<AceAnnotation>) {
    editorRef.asDynamic().current.editor.getSession().setAnnotations(aceAnnotations)
}