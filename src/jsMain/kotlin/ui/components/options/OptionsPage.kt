package ui.components.options

import api.sendIGsRequest
import api.sendVersionsRequest
import api.validateTxServer
import constants.Snomed
import css.const.BORDER_GRAY
import css.const.HIGHLIGHT_GRAY
import kotlinx.coroutines.*
import kotlinx.css.*
import kotlinx.css.properties.border
import mainScope
import model.CliContext
import model.PackageInfo
import react.*
import styled.StyleSheet
import styled.css
import styled.styledDiv
import ui.components.options.menu.checkboxWithDetails
import ui.components.options.menu.dropdownWithExplanation
import ui.components.options.menu.textEntryField
import ui.components.tabs.heading

const val TERMINOLOGY_SERVER_ERROR = "Server capability statement does not indicate it is a valid terminology server."
private const val TERMINOLOGY_CHECK_TIME_LIMIT = 20000L

external interface OptionsPageProps : RProps {
    var cliContext: CliContext
    var update: (CliContext) -> Unit
}

class OptionsPageState : RState {
    var igList = mutableListOf<PackageInfo>()
    var igPackageNameList = mutableListOf<Pair<String, Boolean>>()
    var selectedIgSet = mutableSetOf<PackageInfo>()
    var fhirVersionsList = mutableListOf<Pair<String, Boolean>>()
    var snomedVersionList = mutableListOf<Pair<String, Boolean>>()
}

class OptionsPage : RComponent<OptionsPageProps, OptionsPageState>() {

    init {
        state = OptionsPageState()
        mainScope.launch {
            val igResponse = sendIGsRequest()
            val versionsResponse = sendVersionsRequest()
            setState {
                igList = igResponse.packageInfo
                igPackageNameList = igResponse.packageInfo.map { Pair(it.id!!, false)}.toMutableList()
                fhirVersionsList = versionsResponse.versions
                    .map { Pair(it, props.cliContext.getTargetVer() == it) }
                    .toMutableList()
                snomedVersionList = Snomed.values()
                    .map { Pair("${it.name} - ${it.code}", props.cliContext.getSnomedCTCode() == it.code) }
                    .toMutableList()
            }
        }
    }

    override fun RBuilder.render() {
        styledDiv {
            css {
                +OptionsPageStyle.optionsContainer
            }
            heading {
                text = "Flags"
            }
            styledDiv {
                css {
                    +OptionsPageStyle.optionsSubSection
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
                        +OptionsPageStyle.optionsDivider
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
                        +OptionsPageStyle.optionsDivider
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
                        +OptionsPageStyle.optionsDivider
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
                        +OptionsPageStyle.optionsDivider
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
                    +OptionsPageStyle.optionsSubSection
                }
                igSelector {
                    fhirVersion = props.cliContext.getTargetVer()
                    igList = state.igList
                    igPackageNameList = state.igPackageNameList
                    onUpdatePackageName = {igPackageName, selected ->
                        setState {
                            igPackageNameList = state.igPackageNameList.map{ Pair(it.first, it.first == igPackageName)}.toMutableList()
                        }
                    }
                    selectedIgSet = state.selectedIgSet
                    onUpdateIg = { igPackageInfo, selected ->
                        if (selected) {
                            setState {
                                selectedIgSet = selectedIgSet.plus(igPackageInfo).toMutableSet()
                            }
                        } else {
                            setState {
                                selectedIgSet = selectedIgSet.minus(igPackageInfo).toMutableSet()
                            }
                        }
                        props.update(if (selected) props.cliContext.addIg(PackageInfo.igLookupString(igPackageInfo)) else props.cliContext.removeIg(PackageInfo.igLookupString(igPackageInfo)))
                    }
                }
            }
            heading {
                text = "Other Settings"
            }
            styledDiv {
                css {
                    +OptionsPageStyle.optionsSubSection
                }
                dropdownWithExplanation {
                    defaultLabel = "Version"
                    explanation =
                        "The validator checks the resource against the base specification. By default, this is v4.0.1 of the specification."
                    itemList = state.fhirVersionsList
                    heading = "Select FHIR Version"
                    onItemSelected = { version ->
                        setState {
                            props.cliContext.setTargetVer(version)
                            props.cliContext.setSv(version)
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
                        +OptionsPageStyle.otherSettingsDivider
                    }
                }
                dropdownWithExplanation {
                    defaultLabel = "Version"
                    explanation =
                        "You can specify which edition of SNOMED CT for the terminology server to use when doing SNOMED CT Validation."
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
                        +OptionsPageStyle.otherSettingsDivider
                    }
                }
                textEntryField {
                    buttonLabel = "Verify"
                    currentEntry = props.cliContext.getTxServer()
                    explanation =
                        "The validation engine uses a terminology server to validate codes from large external terminologies such as SNOMED CT, LOINC, RxNorm, etc. By default, the terminology server used is tx.fhir.org, which supports most of these terminologies. If you want to use another terminology server, you can specify one here. As a warning, the server will check that the CapabilityStatement of the provided server is set correctly."
                    heading = "Set Terminology Server"
                    onSubmitEntry = { url ->
                        GlobalScope.async {
                            val txServerOutcome = async { checkTxServer(url) }
                            if (txServerOutcome.await()) {
                                props.update(props.cliContext.setTxServer(url))
                                true
                            } else {
                                false
                            }
                        }
                    }

                    successMessage = "Terminology server validated successfully!"
                    errorMessage = "Terminology server could not be validated!"
                }
            }
        }
    }

    private suspend fun checkTxServer(txUrl: String): Boolean {
        var validTxServer = false
        try {
            withTimeout(TERMINOLOGY_CHECK_TIME_LIMIT) {
                val response = validateTxServer(txUrl)
                if (response.validTxServer) {
                    props.update(props.cliContext.setTxServer(response.url))
                    validTxServer = true
                }
            }
        } catch (e: TimeoutCancellationException) {
            println("Connection attempt to $txUrl resulted in timeout!")
        } catch (e: Exception) {
            println("Exception ${e.message}")
        } finally {
            return validTxServer
        }
    }
}

/**
 * React Component Builder
 */
fun RBuilder.optionsPage(handler: OptionsPageProps.() -> Unit): ReactElement {
    return child(OptionsPage::class) {
        this.attrs(handler)
    }
}

/**
 * CSS
 */
object OptionsPageStyle : StyleSheet("OptionsPageStyle", isStatic = true) {
    val optionsContainer by css {
        display = Display.flex
        flexDirection = FlexDirection.column
        margin(horizontal = 32.px)
        marginBottom = 32.px
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
}