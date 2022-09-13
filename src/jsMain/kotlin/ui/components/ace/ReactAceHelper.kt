package ui.components.ace


import react.RReadableRef


fun gotoLine(editorRef: RReadableRef<Nothing>, line: Int) {
    editorRef.asDynamic().current.editor.gotoLine(line)
}

fun scrollToLine(editorRef: RReadableRef<Nothing>, line: Int) {
    editorRef.asDynamic().current.editor.scrollToLine(line, true, true, null)
}

fun setAnnotations(editorRef: RReadableRef<Nothing>, aceAnnotations : Array<AceAnnotation>) {
    editorRef.asDynamic().current.editor.getSession().setAnnotations(aceAnnotations)
}