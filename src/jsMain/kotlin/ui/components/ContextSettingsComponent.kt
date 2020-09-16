package ui.components

import css.ContextSettingsStyle
import css.TextStyle
import css.const.GRAY_300
import kotlinx.css.*
import kotlinx.css.properties.border
import model.CliContext
import react.*
import styled.css
import styled.styledDiv
import styled.styledHeader

external interface ContextSettingsProps : RProps {
    var cliContext: CliContext
    var update: (CliContext) -> Unit
}

class ContextSettingsComponent : RComponent<ContextSettingsProps, RState>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                +ContextSettingsStyle.mainDiv
            }
            styledHeader {
                css {
                    +TextStyle.h3
                }
                +"Parameters"
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
    }
}

fun RBuilder.contextSettings(handler: ContextSettingsProps.() -> Unit): ReactElement {
    return child(ContextSettingsComponent::class) {
        this.attrs(handler)
    }
}
