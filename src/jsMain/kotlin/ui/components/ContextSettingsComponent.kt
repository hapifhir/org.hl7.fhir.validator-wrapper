package ui.components

import api.sendIGsRequest
import api.sendVersionsRequest
import api.validateTxServer
import constants.Snomed
import css.component.ContextSettingsStyle
import css.text.TextStyle
import css.widget.CheckboxStyle
import kotlinx.coroutines.launch
import kotlinx.css.*
import kotlinx.html.js.onClickFunction
import mainScope
import model.CliContext
import react.*
import styled.*
import ui.components.generic.*

const val TERMINOLOGY_SERVER_ERROR = "Server capability statement does not indicate it is a valid terminology server."

external interface ContextSettingsProps : RProps {
    var cliContext: CliContext
    var update: (CliContext) -> Unit
}

class ContextSettingsState : RState {
    var igList = mutableListOf<MultiChoiceSelectableItem>()
    var fhirVersionsList = mutableListOf<ChoiceSelectableItem>()
    var snomedList = mutableListOf<ChoiceSelectableItem>()
    var implementationGuideDetailsOpen: Boolean = false
}

class ContextSettingsComponent : RComponent<ContextSettingsProps, ContextSettingsState>() {

    init {
        state = ContextSettingsState()
        mainScope.launch {
            val igResponse = sendIGsRequest()
            val versionsResponse = sendVersionsRequest()
            setState {
                igList = igResponse.igs
                    .map { MultiChoiceSelectableItem(value = it, selected = props.cliContext.getIgs().contains(it)) }
                    .toMutableList()
                fhirVersionsList = versionsResponse.versions
                    .map { ChoiceSelectableItem(value = it, selected = props.cliContext.getTargetVer() == it) }
                    .toMutableList()
                snomedList = Snomed.values()
                    .map { ChoiceSelectableItem(value = "${it.name} - ${it.code}", selected = props.cliContext.getSnomedCTCode() == it.code) }
                    .toMutableList()
                fhirVersionsList.forEach { println(it) }
                props.cliContext.prettyPrint()
            }
        }
    }

    override fun RBuilder.render() {
        styledDiv {
            css {
                +ContextSettingsStyle.mainDiv
            }
            styledHeader {
                css {
                    +TextStyle.h3
                }
                +"Flags"
            }
            checkboxInput {
                settingName = "Native Validation (doNative)"
                settingDescription = "By default, the validation engine only validates using the FHIR structures and " +
                        "profiles. The publication processes also generate a variety of xml, json and RDF schemas. You can " +
                        "ask the validator to validate against these as well using this option. Note that there is nothing " +
                        "in these schemas that is not validated directly by the engine itself anyway, so the main use for " +
                        "this is to see the kind of errors that would be reported from these schemas by other software."
                selected = props.cliContext.isDoNative()
                onChange = {
                    props.cliContext.setDoNative(it)
                    props.update(props.cliContext)
                }
            }
            checkboxInput {
                settingName = "Must Support (hintAboutMustSupport)"
                settingDescription =
                    "In some cases (e.g. when creating examples for implementation guides or when checking" +
                            " for potential interoperability issues with a new communication partner), it can be useful to know " +
                            "when data elements are present in an instance when those elements are not \"mustSupport\" in the " +
                            "profile(s) the instance is being validated against. Identifying situations where this occurs might " +
                            "drive a change to the profile or cause a designer to drop an element from the instance. In other " +
                            "cases, the presence of the element can be fine and the information message ignored."
                selected = props.cliContext.isHintAboutNonMustSupport()
                onChange = {
                    props.cliContext.setHintAboutNonMustSupport(it)
                    props.update(props.cliContext)
                }
            }
            checkboxInput {
                settingName = "Assume Valid Rest References (assumeValidRestReferences)"
                settingDescription =
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
                    props.cliContext.setAssumeValidRestReferences(it)
                    props.update(props.cliContext)
                }
            }
            checkboxInput {
                settingName = "Extensible Binding Warnings (noExtensibleBindingWarnings)"
                settingDescription =
                    "When the validator encounters a code that is not part of an extensible binding, it " +
                            "adds a warning to suggest that the code be reviewed. The validator can't determine whether the " +
                            "meaning of the code makes it an inappropriate extension, or not; this requires human review. " +
                            "Hence, the warning. But the code may be valid - that's why extensible is defined - so in some " +
                            "operational uses of the validator, it is appropriate to turn these warnings off"
                selected = props.cliContext.isNoExtensibleBindingMessages()
                onChange = {
                    props.cliContext.setNoExtensibleBindingMessages(it)
                    props.update(props.cliContext)
                }
            }
            checkboxInput {
                settingName = "Show Times (showTimes)"
                settingDescription =
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
        styledDiv {
            css {
                +ContextSettingsStyle.mainDiv
            }
            styledDiv {
                css {
                    +ContextSettingsStyle.sectionTitleBar
                }
                attrs {
                    onClickFunction = {
                        setState {
                            implementationGuideDetailsOpen = !implementationGuideDetailsOpen
                        }
                    }
                }
                styledHeader {
                    css {
                        +TextStyle.h3
                    }
                    +"Implementation Guides"
                }
                styledDiv {
                    css {
                        +ContextSettingsStyle.dropDownArrowDiv
                    }
                    styledImg {
                        css {
                            +ContextSettingsStyle.dropDownArrow
                        }
                        attrs {
                            src = if (state.implementationGuideDetailsOpen) {
                                "images/arrow_up.svg"
                            } else {
                                "images/arrow_down.svg"
                            }
                        }
                    }
                }
            }
            styledDiv {
                css {
                    +CheckboxStyle.propertiesDetails
                    display = if (state.implementationGuideDetailsOpen) {
                        Display.flex
                    } else {
                        Display.none
                    }
                }
                styledSpan {
                    css {
                        +TextStyle.codeDark
                        if (state.implementationGuideDetailsOpen) {
                            padding(1.rem)
                        }
                    }
                    +"You can validate against one or more published implementation guides. Select from the dropdown menu below."
                }
            }
            styledDiv {
                css {
                    +ContextSettingsStyle.dropDownAndSelectedIgDiv
                }
                dropDownMultiChoice {
                    choices = state.igList
                    buttonLabel = "Select IG"
                    onSelected = { selected, list ->
                        setState {
                            igList = list
                            props.update(props.cliContext.addIg(selected))
                        }
                    }
                }
                styledDiv {
                    css {
                        +ContextSettingsStyle.selectedIgsDiv
                        if (state.igList.any { it.selected }) {
                            padding(top = 1.rem)
                        }
                    }
                    state.igList.filter { it.selected }.forEach { igState ->
                        igUrlDisplay {
                            igUrl = igState.value
                            onDelete = {
                                deselectIg(igState.value)
                            }
                        }
                    }
                }
            }
        }
        styledDiv {
            css {
                +ContextSettingsStyle.mainDiv
            }
            styledHeader {
                css {
                    +TextStyle.h3
                    paddingBottom = 0.5.rem
                }
                +"Other Settings"
            }
            styledDiv {
                headingWithDropDownExplanation {
                    heading = "Select FHIR Version"
                    explanation =
                        "The validator checks the resource against the base specification. By default, this is the current build version of the specification. You probably don't want to validate against that version, so the first thing to do is to specify which version of the spec to use."
                }
                dropDownChoice {
                    onSelected = { selected, list ->
                        setState {
                            props.cliContext.setTargetVer(selected)
                            state.fhirVersionsList = list
                        }
                    }
                    buttonLabel = "FHIR Version"
                    choices = state.fhirVersionsList
                }
            }
            styledDiv {
                headingWithDropDownExplanation {
                    heading = "Select SNOMED Version"
                    explanation =
                        "You can specify which edition of SNOMED CT for the terminology server to use when doing SNOMED CT Validation."
                }
                dropDownChoice {
                    onSelected = { selected, list ->
                        setState {
                            // Need to just pull out the code from the selected entry
                            props.cliContext.setSnomedCT(selected.replace("[^0-9]".toRegex(), ""))
                            state.snomedList = list
                        }
                    }
                    buttonLabel = "SNOMED"
                    choices = state.snomedList
                }
            }
            styledDiv {
                headingWithDropDownExplanation {
                    heading = "Set Terminology Server"
                    explanation = "The validation engine uses a terminology server to validate codes from large external terminologies such as SNOMED CT, LOINC, RxNorm, etc. By default, the terminology server used is tx.fhir.org, which supports most of these terminologies. If you want to use another terminology server, you can specify one here. As a warning, the server will check that the CapabilityStatement of the provided server is set correctly."
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

    private fun deselectIg(igUrl: String) {
        setState {
            igList.first { it.value == igUrl }.selected = false
        }
        props.update(props.cliContext.removeIg(igUrl))
    }

    private fun checkTxServer(txUrl: String): Pair<Boolean, String> {
        var response = false
        mainScope.launch {
             response = validateTxServer(txUrl)
        }
        return Pair(response, if (response) "" else TERMINOLOGY_SERVER_ERROR);
    }
}

fun RBuilder.contextSettings(handler: ContextSettingsProps.() -> Unit): ReactElement {
    return child(ContextSettingsComponent::class) {
        this.attrs(handler)
    }
}
