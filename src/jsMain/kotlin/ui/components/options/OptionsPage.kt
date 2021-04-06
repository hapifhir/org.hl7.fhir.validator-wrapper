package ui.components.options

import api.validateTxServer
import css.const.*
import css.text.TextStyle
import kotlinx.coroutines.launch
import kotlinx.css.*
import kotlinx.css.properties.border
import kotlinx.css.properties.boxShadow
import mainScope
import model.CliContext
import react.*
import styled.StyleSheet
import styled.css
import styled.styledDiv
import ui.components.generic.headingWithDropDownExplanation
import ui.components.generic.optionEntryField
import ui.components.options.menu.checkboxWithDetails
import ui.components.options.menu.dropdownWithExplanation
import ui.components.tabs.heading

const val TERMINOLOGY_SERVER_ERROR = "Server capability statement does not indicate it is a valid terminology server."

external interface OptionsPageProps : RProps {
    var cliContext: CliContext
    var update: (CliContext) -> Unit
}

class OptionsPageState : RState {
    var igList = mutableListOf<Pair<String, Boolean>>()
    var fhirVersionsList = mutableListOf<Pair<String, Boolean>>()
    var snomedVersionList = mutableListOf<Pair<String, Boolean>>()
    var implementationGuideDetailsOpen: Boolean = false
}

class OptionsPage : RComponent<OptionsPageProps, OptionsPageState>() {

    init {
        state = OptionsPageState()
        state.igList = mutableListOf(
            Pair("aaa.aaa.aaa", false),
            Pair("bbb.bbb.bbb", false),
            Pair("ccc.ccc.ccc", false),
            Pair("ddd.ddd.ddd", false),
            Pair("eee.eee.eee", false),
            Pair("fff.fff.fff", false),
        )
        state.fhirVersionsList = mutableListOf(
            Pair("v1", false),
            Pair("v2", false),
            Pair("v3", false),
            Pair("v4", false),
            Pair("v5", false),
            Pair("v6", false),
        )
        state.snomedVersionList = mutableListOf(
            Pair("snomed1", false),
            Pair("snomed2", false),
            Pair("snomed3", false),
            Pair("snomed4", false),
            Pair("snomed5", false),
            Pair("snomed6", false),
        )
//        mainScope.launch {
//            val igResponse = sendIGsRequest()
//            val versionsResponse = sendVersionsRequest()
//            setState {
//
////            igList = igResponse.igs
////                    .map { Pair(it, props.cliContext.getIgs().contains(it)) }
////                    .toMutableList()
//                fhirVersionsList = versionsResponse.versions
//                    .map { ChoiceSelectableItem(value = it, selected = props.cliContext.getTargetVer() == it) }
//                    .toMutableList()
//                snomedList = Snomed.values()
//                    .map {
//                        ChoiceSelectableItem(value = "${it.name} - ${it.code}",
//                            selected = props.cliContext.getSnomedCTCode() == it.code)
//                    }
//                    .toMutableList()
//                fhirVersionsList.forEach { println(it) }
//                props.cliContext.prettyPrint()
//            }
//        }
    }

    override fun RBuilder.render() {
        styledDiv {
            css {
                +ContextSettingsStyle.optionsContainer
            }
            heading {
                text = "Flags"
            }
            styledDiv {
                css {
                    +ContextSettingsStyle.optionsSubSection
                }
                checkboxWithDetails {
                    name = "Native Validation (doNative)"
                    description = "By default, the validation engine only validates using the FHIR structures and " +
                            "profiles. The publication processes also generate a variety of xml, json and RDF schemas. You can " +
                            "ask the validator to validate against these as well using this option. Note that there is nothing " +
                            "in these schemas that is not validated directly by the engine itself anyway, so the main use for " +
                            "this is to see the kind of errors that would be reported from these schemas by other software."
                    selected = props.cliContext.isDoNative()
                    onChange = {
                        props.update(props.cliContext.setDoNative(it))
                    }
                }
                styledDiv {
                    css {
                        +ContextSettingsStyle.optionsDivider
                    }
                }
                checkboxWithDetails {
                    name = "Must Support (hintAboutMustSupport)"
                    description =
                        "In some cases (e.g. when creating examples for implementation guides or when checking" +
                                " for potential interoperability issues with a new communication partner), it can be useful to know " +
                                "when data elements are present in an instance when those elements are not \"mustSupport\" in the " +
                                "profile(s) the instance is being validated against. Identifying situations where this occurs might " +
                                "drive a change to the profile or cause a designer to drop an element from the instance. In other " +
                                "cases, the presence of the element can be fine and the information message ignored."
                    selected = props.cliContext.isHintAboutNonMustSupport()
                    onChange = {
                        props.update(props.cliContext.setHintAboutNonMustSupport(it))
                    }
                }
                styledDiv {
                    css {
                        +ContextSettingsStyle.optionsDivider
                    }
                }
                checkboxWithDetails {
                    name = "Assume Valid Rest References (assumeValidRestReferences)"
                    description =
                        "If the validator can't fetch target resources, it can at least be instructed validate" +
                                " that the target resource is correct. For instance, if the allowed target types are (Patient or " +
                                "RelatedPerson), and the reference is Group/12345, then this would be usually be an error. However " +
                                "this is not actually explicitly stated in the FHIR specification - a reference could be to " +
                                "http://some.server/somewhere - it doesn't need to look like a valid FHIR RESTful reference. In " +
                                "fact, it's not an error even if it happens to. So by default, the validator can assume nothing " +
                                "about the type of resource from the content of the reference itself. However most implementers " +
                                "do follow those rules - a reference to either Group/12345 or http://some-server/somwhere/Group/12345 " +
                                "is definitely to a Group resource. (and it's definitely recommended to follow these rules). The " +
                                "flag assumeValidRestReferences instructs the validator to use the type found in references that " +
                                "look like valid RESTful URLs when validating the type of the reference."
                    selected = props.cliContext.isAssumeValidRestReferences()
                    onChange = {
                        props.update(props.cliContext.setAssumeValidRestReferences(it))
                    }
                }
                styledDiv {
                    css {
                        +ContextSettingsStyle.optionsDivider
                    }
                }
                checkboxWithDetails {
                    name = "Extensible Binding Warnings (noExtensibleBindingWarnings)"
                    description =
                        "When the validator encounters a code that is not part of an extensible binding, it " +
                                "adds a warning to suggest that the code be reviewed. The validator can't determine whether the " +
                                "meaning of the code makes it an inappropriate extension, or not; this requires human review. " +
                                "Hence, the warning. But the code may be valid - that's why extensible is defined - so in some " +
                                "operational uses of the validator, it is appropriate to turn these warnings off"
                    selected = props.cliContext.isNoExtensibleBindingMessages()
                    onChange = {
                        props.update(props.cliContext.setNoExtensibleBindingMessages(it))
                    }
                }
                styledDiv {
                    css {
                        +ContextSettingsStyle.optionsDivider
                    }
                }
                checkboxWithDetails {
                    name = "Show Times (showTimes)"
                    description =
                        "When the validator encounters a code that is not part of an extensible binding, it " +
                                "adds a warning to suggest that the code be reviewed. The validator can't determine whether the " +
                                "meaning of the code makes it an inappropriate extension, or not; this requires human review. " +
                                "Hence, the warning. But the code may be valid - that's why extensible is defined - so in some " +
                                "operational uses of the validator, it is appropriate to turn these warnings off"
                    selected = props.cliContext.isShowTimes()
                    onChange = {
                        props.cliContext.setShowTimes(it)
                        props.update(props.cliContext)
                    }
                }
            }
            heading {
                text = "Implementation Guides"
            }
            styledDiv {
                css {
                    +ContextSettingsStyle.optionsSubSection
                }
                igSelector {
                    igList = state.igList
                    onUpdateIg = { igLabel, selected ->
                        state.igList.find { pair -> pair.first == igLabel }?.let { pair ->
                            setState {
                                igList[igList.indexOf(pair)] = pair.copy(second = selected)
                            }
                        }
                        props.update(if (selected) props.cliContext.addIg(igLabel) else props.cliContext.removeIg(
                            igLabel))
                    }
                }
            }
            heading {
                text = "Other Settings"
            }
            styledDiv {
                css {
                    +ContextSettingsStyle.optionsSubSection
                }
                dropdownWithExplanation {
                    defaultLabel = "Version"
                    explaination = "The validator checks the resource against the base specification. By default, this is the current build version of the specification. You probably don't want to validate against that version, so the first thing to do is to specify which version of the spec to use."
                    itemList = state.fhirVersionsList
                    heading = "Select FHIR Version"
                    onItemSelected = { version ->
                        setState {
                            props.cliContext.setTargetVer(version)
                            state.fhirVersionsList.forEach {
                                fhirVersionsList[fhirVersionsList.indexOf(it)] =
                                    when (it.first) {
                                        version -> it.copy(second = true)
                                        else -> it.copy(second = false)
                                    }

                            }
                        }
                    }
                }
                styledDiv {
                    css {
                        +ContextSettingsStyle.otherSettingsDivider
                    }
                }
                dropdownWithExplanation {
                    defaultLabel = "Version"
                    explaination = "You can specify which edition of SNOMED CT for the terminology server to use when doing SNOMED CT Validation."
                    itemList = state.snomedVersionList
                    heading = "Select SNOMED Version"
                    onItemSelected = { version ->
                        setState {
                            props.cliContext.setSnomedCT(version.replace("[^0-9]".toRegex(), ""))
                            state.snomedVersionList.forEach {
                                snomedVersionList[snomedVersionList.indexOf(it)] =
                                    when (it.first) {
                                        version -> it.copy(second = true)
                                        else -> it.copy(second = false)
                                    }

                            }
                        }
                    }
                }
                styledDiv {
                    css {
                        +ContextSettingsStyle.otherSettingsDivider
                    }
                }
            }
        }
        styledDiv {

            styledDiv {
                headingWithDropDownExplanation {
                    heading = "Set Terminology Server"
                    explanation =
                        "The validation engine uses a terminology server to validate codes from large external terminologies such as SNOMED CT, LOINC, RxNorm, etc. By default, the terminology server used is tx.fhir.org, which supports most of these terminologies. If you want to use another terminology server, you can specify one here. As a warning, the server will check that the CapabilityStatement of the provided server is set correctly."
                }
                optionEntryField {
                    submitEntry = { url ->
                        println(url)
                        checkTxServer(url)
                    }
                    defaultValue = props.cliContext.getTxServer()
                }
            }
        }

        styledDiv {
            css {
                height = 4.rem
            }
        }
    }

    private fun checkTxServer(txUrl: String): Pair<Boolean, String> {
        var response = false
        mainScope.launch {
            response = validateTxServer(txUrl)
        }
        return Pair(response, if (response) "" else TERMINOLOGY_SERVER_ERROR);
    }
}

/**
 * React Component Builder
 */
fun RBuilder.contextSettings(handler: OptionsPageProps.() -> Unit): ReactElement {
    return child(OptionsPage::class) {
        this.attrs(handler)
    }
}

/**
 * CSS
 */
object ContextSettingsStyle : StyleSheet("ContextSettingsStyle", isStatic = true) {
    val optionsContainer by css {
        display = Display.flex
        flexDirection = FlexDirection.column
        margin(horizontal = 32.px)
    }
    val optionsSubSection by css {
        display = Display.flex
        flexDirection = FlexDirection.column
        padding(16.px)
        border(width = 1.px, style = BorderStyle.solid, color = BORDER_GRAY, borderRadius = 2.px)
    }
    val optionsDivider by css {
        width = 100.pct
        height = 1.px
        backgroundColor = HIGHLIGHT_GRAY
        margin(vertical = 8.px)
    }
    val otherSettingsDivider by css {
        width = 100.pct
        height = 1.px
        backgroundColor = HIGHLIGHT_GRAY
        margin(vertical = 16.px)
    }
    val sectionTitleBar by css {
        display = Display.flex
        flexDirection = FlexDirection.row
    }
    val dropDownArrowDiv by css {
        display = Display.flex
        flex(flexGrow = 1.0)
        flexDirection = FlexDirection.row
        justifyContent = JustifyContent.flexEnd
    }
    val dropDownArrow by css {
        width = 24.px
        height = 24.px
        alignSelf = Align.center
    }
    val dropDownAndSelectedIgDiv by css {
        display = Display.flex
        flexDirection = FlexDirection.column
        paddingTop = 0.5.rem
    }

    val dropDownButtonAndContentDiv by css {
        display = Display.flex
        flexDirection = FlexDirection.column
    }

    val dropbtn by css {
        backgroundColor = GRAY_700
        color = WHITE
        padding(1.rem)
        fontSize = 1.rem
        borderStyle = BorderStyle.none
        cursor = Cursor.pointer
        minWidth = 20.pct
        hover {
            children("div") {
                display = Display.block
            }
        }
    }

    val dropdown by css {
        position = Position.relative
        display = Display.inlineBlock
    }

    val dropdownContent by css {
        display = Display.none
        position = Position.absolute
        backgroundColor = GRAY_700
        overflowY = Overflow.scroll
        minWidth = 160.px
        maxHeight = 240.px
        boxShadow(color = SHADOW, offsetX = 0.px, offsetY = 5.px, blurRadius = 5.px)
        zIndex = 1
        children("span") {
            padding(vertical = 12.px, horizontal = 16.px)
            +TextStyle.codeLight
            display = Display.block
            hover {
                backgroundColor = GRAY_400
            }
        }
    }

    val selectedIgsDiv by css {
        display = Display.flex
        flexDirection = FlexDirection.row
        flexWrap = FlexWrap.wrap
    }
}