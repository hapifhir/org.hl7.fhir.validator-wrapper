package uicomponents

import constants.MIMEType
import css.TabBarStyle
import kotlinext.js.getOwnPropertyNames
import kotlinx.css.*
import model.FileInfo
import org.w3c.dom.events.EventTarget
import org.w3c.files.File
import org.w3c.files.FileReader
import react.*
import styled.StyleSheet
import styled.*

/**
 * 
 */
external interface FileUploadTabProps: RProps {
    var active: Boolean
    var onValidate: (List<FileInfo>) -> Unit
}

class FileUploadTab: RComponent<FileUploadTabProps, RState>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                display = if (props.active) Display.flex else Display.none
                +TabBarStyle.body
            }
            fileUploadComponent {
                onValidate = {
                    props.onValidate(creatFileInfoList(it))
                }
            }
        }
    }
}

fun creatFileInfoList(files: MutableList<File>): MutableList<FileInfo> {
    var fileInfos = mutableListOf<FileInfo>()
    files.forEach {
        fileInfos.add(parseFileInfo(it))
    }
    return fileInfos
}

fun parseFileInfo(file: File): FileInfo {
    val fileInfo = FileInfo()
    fileInfo.setFileName(file.name).setFileType(MIMEType.get(file.type)?.fhirType!!)
    readFile(file, callback = {
        // TODO this is not the safest...but I can't figure out a better way to do this right now
        fileInfo.setFileContent(it)
    })
    return fileInfo
}

fun readFile(file: File, callback: (String) -> Unit) {
    val reader = FileReader()
    reader.addEventListener("load", callback = {
        val result = reader.result
        callback(result as String)
    })
    reader.readAsDataURL(file)
}

/**
 */
fun RBuilder.fileUploadTab(handler: FileUploadTabProps.() -> Unit): ReactElement {
    return child(FileUploadTab::class) {
        this.attrs(handler)
    }
}