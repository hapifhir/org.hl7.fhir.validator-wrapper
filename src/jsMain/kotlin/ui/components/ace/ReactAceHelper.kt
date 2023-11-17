package ui.components.ace

import react.RefObject

fun scrollTo(editorRef: RefObject<Nothing>, row: Int, column:Int) {
    editorRef.asDynamic().current.editor.gotoLine(row)
    editorRef.asDynamic().current.editor.moveCursorTo(row - 1, column - 1)
    editorRef.asDynamic().current.editor.centerSelection()
}

fun setAnnotations(editorRef: RefObject<Nothing>, aceAnnotations : Array<AceAnnotation>) {
    editorRef.asDynamic().current.editor.getSession().setAnnotations(aceAnnotations)
}