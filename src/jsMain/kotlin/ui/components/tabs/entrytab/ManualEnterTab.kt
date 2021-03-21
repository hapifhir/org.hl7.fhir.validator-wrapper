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
import ui.components.buttons.toggleButton
import ui.components.validation.codeissuedisplay.codeIssueDisplay
import ui.components.validation.issuelist.filteredIssueEntryList
import ui.components.validation.issuelist.issueEntry
import ui.components.validation.issuelist.issueEntryList
import ui.components.validation.issuelist.issueFilterButtonBar

external interface ManualEnterTabProps : RProps {}

class ManualEnterTab : RComponent<ManualEnterTabProps, RState>() {
    // TODO delete and map to actual props
    val results = emptyList<ValidationOutcome>()
    val temp = ValidationOutcome()
            .setFileInfo(FileInfo().setFileName("test_file1.json").setFileContent(
                    "Unlike flying or astral projection," +
                    "\n\twalking through walls is a totally earth-related craft," +
                    "\n\t\tbut a lot more interesting than pot making or driftwood lamps." +
                    "\n\t\t\tI got started at a picnic up in Bowstring in the northern part of the state." +
                    "\n\t\t\t\tA fellow walked through a brick wall right there in the park." +
                    "\n\t\t\tI said, 'Say, I want to try that.'" +
                    "\n\t\tStone walls are best, then brick and wood." +
                    "\n\tWooden walls with fiberglass insulation and steel doors aren't so good." +
                    "\nThey won't hurt you." +
                    "\n\tIf your wall walking is done properly," +
                    "\n\t\tboth you and the wall are left intact." +
                    "\n\t\t\tIt is just that they aren't pleasant somehow." +
                    "\n\t\tThe worst things are wire fences," +
                    "\n\tmaybe it's the molecular structure of the alloy or just the amount of give in a fence," +
                    "\nI don't know, but I've torn my jacket and lost my hat in a lot of fences." +
                    "\n\tThe best approach to a wall is, first, two hands placed flat against the surface;" +
                    "\n\t\tit's a matter of concentration and just the right pressure." +
                    "\n\tYou will feel the dry," +
                    "\ncool inner wall with your fingers," +
                    "\nthen there is a moment of total darkness before you step through on the other side."))
            .setValidated(true)
            .setValidating(false)

    override fun RBuilder.render() {
        val msg1 = ValidationMessage()
        msg1.setLevel(level = IssueSeverity.FATAL).setMessage("This is the fatal message!")
        msg1.setLine(5)

        val msg2 = ValidationMessage()
        msg2.setLevel(level = IssueSeverity.ERROR).setMessage("This is the error message!")
        msg2.setLine(5)

        val msg3 = ValidationMessage()
        msg3.setLevel(level = IssueSeverity.WARNING).setMessage("This is the warning message!")
        msg3.setLine(15)

        val msg4 = ValidationMessage()
        msg4.setLevel(level = IssueSeverity.INFORMATION).setMessage("This is the information message!")
        msg4.setLine(20)

        temp.setMessages(listOf(msg1, msg2, msg3, msg4))

        styledDiv {
            css {
                // If the tab is currently displayed on screen, we define a layout type, otherwise, we set to none
                display = Display.flex
                alignItems = Align.flexStart
                +TabStyle.tabContent
            }
            codeIssueDisplay {
                validationOutcome = temp
                messageFilter = MessageFilter(showFatal = true)
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