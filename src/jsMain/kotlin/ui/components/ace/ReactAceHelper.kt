package ui.components.ace

import react.MutableRefObject


fun gotoLine(editorRef: MutableRefObject<Nothing>, line: Int) {
    editorRef.asDynamic().current.editor.gotoLine(line)
}

fun scrollToLine(editorRef: MutableRefObject<Nothing>, line: Int) {
    editorRef.asDynamic().current.editor.scrollToLine(line, true, true, null)
}

fun setAnnotations(editorRef: MutableRefObject<Nothing>, aceAnnotations : Array<AceAnnotation>) {
    editorRef.asDynamic().current.editor.getSession().setAnnotations(aceAnnotations)
}