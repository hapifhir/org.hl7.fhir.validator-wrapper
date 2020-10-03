package ui.components

import api.sendIGsRequest
import api.sendVersionsRequest
import css.component.ContextSettingsStyle
import css.text.TextStyle
import css.widget.CheckboxStyle
import kotlinx.coroutines.launch
import kotlinx.css.*
import kotlinx.html.id
import kotlinx.html.js.onClickFunction
import mainScope
import model.CliContext
import react.*
import styled.*
import ui.components.generic.SelectableItem
import ui.components.generic.checkboxInput
import ui.components.generic.dropDownChoice
import ui.components.generic.dropDownMultiChoice

external interface ContextSettingsProps : RProps {
    var cliContext: CliContext
    var update: (CliContext) -> Unit
}

class ContextSettingsState : RState {
    var igList = mutableListOf<SelectableItem>()
    var fhirVersionsList = mutableListOf<String>()
    var implementationGuideDetailsOpen: Boolean = false
}

class ContextSettingsComponent : RComponent<ContextSettingsProps, ContextSettingsState>() {

    init {
        state = ContextSettingsState()
        mainScope.launch {
            val igResponse = sendIGsRequest()
            val versionsResponse = sendVersionsRequest()
            setState {
                igList = igResponse.igs.map { SelectableItem(value = it, selected = props.cliContext.getIgs().contains(it)) }.toMutableList()
                fhirVersionsList = versionsResponse.versions
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
                attrs {
                    id = "setting_info_drawer"
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
                    onSelected = { selected, _ ->
                        selectIg(selected)
                    }
                }

                styledDiv {
                    css {
                        +ContextSettingsStyle.selectedIgsDiv
                        if (state.igList.filter {it.selected}.toList().isNotEmpty()) {
                            padding(top = 1.rem)
                        }
                    }
                    state.igList.filter {it.selected}.forEach { igState ->
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
            dropDownChoice {
                onSelected = {
                    println("pressed $it")
                }
                defaultButtonLabel = "FHIR Version"
                choices = state.fhirVersionsList
            }
        }
        styledDiv {
            css {
                height = 4.rem
            }
        }
    }

    private fun selectIg(igUrl: String) {
        setState {
            igList.first { it.value == igUrl }.selected = true
        }
        props.update(props.cliContext.addIg(igUrl))
    }

    private fun deselectIg(igUrl: String) {
        setState {
            igList.first { it.value == igUrl }.selected = false
        }
        props.update(props.cliContext.removeIg(igUrl))
    }
}


fun RBuilder.contextSettings(handler: ContextSettingsProps.() -> Unit): ReactElement {
    return child(ContextSettingsComponent::class) {
        this.attrs(handler)
    }
}
