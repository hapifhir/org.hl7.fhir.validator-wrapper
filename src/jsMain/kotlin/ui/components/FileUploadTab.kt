package ui.components

import css.TabBarStyle
import kotlinx.css.Display
import kotlinx.css.display
import model.FileInfo
import org.w3c.files.File
import react.*
import styled.css
import styled.styledDiv
import utils.FileEventListener
import utils.FileLoadState
import utils.convertToFileInfo
import utils.parseFile

external interface FileUploadTabProps : RProps {
    var active: Boolean
    var onValidate: (List<FileInfo>) -> Unit
}

class FileUploadTabState : RState {
    var validating: Boolean = false
    var loadedFiles: Int = 0
    var fileLoadResultMap: HashMap<File, FileLoadState> = HashMap()
}

class FileUploadTab : RComponent<FileUploadTabProps, FileUploadTabState>(), FileEventListener {

    init {
        state = FileUploadTabState()
    }

    override fun RBuilder.render() {
        styledDiv {
            css {
                display = if (props.active) Display.flex else Display.none
                +TabBarStyle.body
            }
            fileUploadComponent {
                onValidate = { fileList ->
                    setState {
                        validating = true
                        fileLoadResultMap.clear()
                        fileList.forEach {
                            fileLoadResultMap[it] = FileLoadState(file = it)
                        }
                    }
                    loadFilesForValidation(fileList)
                }
            }
        }
    }

    private fun loadFilesForValidation(files: MutableList<File>): MutableList<FileInfo> {
        setState {
            loadedFiles = 0
        }
        var fileInfos = mutableListOf<FileInfo>()
        files.forEach {
            parseFile(it, this)
        }
        return fileInfos
    }

    override fun onLoadStart(file: File) {
        println("OnLoadStart -> ${file.name}")
    }

    override fun onLoadProgress(file: File, loaded: Double, total: Double) {
        println("onLoadProgress -> ${file.name}")
        setState {
            fileLoadResultMap[file]?.loaded = loaded
            fileLoadResultMap[file]?.total = total
        }
        println("progress -> ${(loaded / total) * 100}")
    }

    override fun onLoadComplete(file: File, result: String) {
        println("onLoadComplete -> ${file.name}")
        setState {
            fileLoadResultMap[file]?.completed = true
            fileLoadResultMap[file]?.content = result
            loadedFiles++
        }
        evaluateOverallLoadProgress()
    }

    override fun onLoadError(file: File, error: Any?, message: String) {
        println("OnLoadStart -> ${file.name}")
        setState {
            fileLoadResultMap[file]?.error = true
            fileLoadResultMap[file]?.errorMessage = message
            loadedFiles++
        }
        evaluateOverallLoadProgress()
    }

    private fun evaluateOverallLoadProgress() {
        if (state.loadedFiles == state.fileLoadResultMap.size) {
            // TODO deal with errors and displaying progress to user
            val fileInfoList = mutableListOf<FileInfo>()
            state.fileLoadResultMap.values.forEach {
                fileInfoList.add(convertToFileInfo(it))
            }
            props.onValidate(fileInfoList)
            setState {
                validating = false
            }
        }
    }
}

fun RBuilder.fileUploadTab(handler: FileUploadTabProps.() -> Unit): ReactElement {
    return child(FileUploadTab::class) {
        this.attrs(handler)
    }
}