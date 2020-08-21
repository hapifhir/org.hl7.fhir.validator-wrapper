package ui.components

import constants.FhirFormat
import css.TabBarStyle
import kotlinx.css.Display
import kotlinx.css.display
import model.FileInfo
import react.*
import styled.css
import styled.styledDiv

external interface ManualEnterTabProps : RProps {
    var active: Boolean
    var onValidate: (List<FileInfo>) -> Unit
}

class ManualEnterTabState : RState {
    var files: MutableList<FileInfo> = mutableListOf()
}

class ManualEnterTab : RComponent<ManualEnterTabProps, ManualEnterTabState>() {

    init {
        state = ManualEnterTabState()
    }

    override fun RBuilder.render() {
        styledDiv {
            css {
                display = if (props.active) Display.flex else Display.none
                +TabBarStyle.body
            }
            resourceEntryComponent {
                onSubmit = {
                    setState {
                        println("Generating fileInfo")
                        files.add(
                            FileInfo().setFileName("manually_gen_file").setFileContent(it)
                                .setFileType(FhirFormat.JSON.code)
                        )
                    }
                    props.onValidate(state.files)
                }
            }
        }
    }
}

fun RBuilder.manualEnterTab(handler: ManualEnterTabProps.() -> Unit): ReactElement {
    return child(ManualEnterTab::class) {
        this.attrs(handler)
    }
}