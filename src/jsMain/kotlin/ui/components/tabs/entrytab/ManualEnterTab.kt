package ui.components.tabs.entrytab

import css.component.tabs.TabStyle
import kotlinx.css.Align
import kotlinx.css.Display
import kotlinx.css.alignItems
import kotlinx.css.display
import model.*
import react.*
import styled.css
import styled.styledDiv
import ui.components.FileUploadState
import ui.components.buttons.optionButton
import ui.components.validation.issuelist.filteredIssueEntryList
import ui.components.validation.issuelist.issueEntry
import ui.components.validation.issuelist.issueEntryList
import ui.components.validation.issuelist.issueFilterButtonBar

external interface ManualEnterTabProps : RProps {}

class ManualEnterTab : RComponent<ManualEnterTabProps, RState>() {
    // TODO delete and map to actual props
    val results = emptyList<ValidationOutcome>()
    val temp = ValidationOutcome()
            .setFileInfo(FileInfo().setFileName("test_file1.json"))
            .setValidated(true)
            .setValidating(false)

    override fun RBuilder.render() {
        val msg1 = ValidationMessage()
        msg1.setLevel(level = IssueSeverity.FATAL).setMessage("This is the fatal message!")
        msg1.setLine(100)

        val msg2 = ValidationMessage()
        msg2.setLevel(level = IssueSeverity.ERROR).setMessage("This is the error message!")
        msg2.setLine(200)

        val msg3 = ValidationMessage()
        msg3.setLevel(level = IssueSeverity.WARNING).setMessage("This is the warning message!")
        msg3.setLine(300)

        val msg4 = ValidationMessage()
        msg4.setLevel(level = IssueSeverity.INFORMATION).setMessage("This is the information message!")
        msg4.setLine(400)

        temp.setMessages(listOf(msg1, msg2, msg3, msg4))

        styledDiv {
            css {
                // If the tab is currently displayed on screen, we define a layout type, otherwise, we set to none
                display = Display.flex
                alignItems = Align.flexStart
                +TabStyle.tabContent
            }
            //resourceEntryField { }
            filteredIssueEntryList {
                validationOutcome = temp
            }
//            issueEntryList {
//                validationOutcome = ValidationOutcome().setMessages(listOf(msg1, msg2, msg3, msg4))
//                messageFilter = state.messageFilter
//            }
        }
    }
}

fun RBuilder.manualEnterTab(handler: ManualEnterTabProps.() -> Unit): ReactElement {
    return child(ManualEnterTab::class) {
        this.attrs(handler)
    }
}