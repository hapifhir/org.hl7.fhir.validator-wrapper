package ui.components.options

import Polyglot
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

external interface OptionsPageProps : Props {
    var cliContext: CliContext
    var selectedIgPackageInfo: Set<PackageInfo>
    var updateCliContext: (CliContext) -> Unit
    var updateSelectedIgPackageInfo: (Set<PackageInfo>) -> Unit
    var polyglot: Polyglot
}

class OptionsPageState : State {
    var igList = mutableListOf<PackageInfo>()
    var igPackageNameList = mutableListOf<Pair<String, Boolean>>()
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
                igPackageNameList = getPackageNames(igResponse.packageInfo)
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
                text = props.polyglot.t("options_flags_title")
            }
            styledDiv {
                css {
                    +OptionsPageStyle.optionsSubSection
                }
                checkboxWithDetails {
                    name = props.polyglot.t("options_flags_do_native_title")
                    description = props.polyglot.t("options_flags_do_native_description")
                    selected = props.cliContext.isDoNative()
                    onChange = {
                        props.updateCliContext(props.cliContext.setDoNative(it))
                    }
                }
                styledDiv {
                    css {
                        +OptionsPageStyle.optionsDivider
                    }
                }
                checkboxWithDetails {
                    name = props.polyglot.t("options_flags_must_support_title")
                    description = props.polyglot.t("options_flags_must_support_description")
                    selected = props.cliContext.isHintAboutNonMustSupport()
                    onChange = {
                        props.updateCliContext(props.cliContext.setHintAboutNonMustSupport(it))
                    }
                }
                styledDiv {
                    css {
                        +OptionsPageStyle.optionsDivider
                    }
                }
                checkboxWithDetails {
                    name = props.polyglot.t("options_flags_valid_reference_title")
                    description = props.polyglot.t("options_flags_valid_reference_description")
                    selected = props.cliContext.isAssumeValidRestReferences()
                    onChange = {
                        props.updateCliContext(props.cliContext.setAssumeValidRestReferences(it))
                    }
                }
                styledDiv {
                    css {
                        +OptionsPageStyle.optionsDivider
                    }
                }
                checkboxWithDetails {
                    name = props.polyglot.t("options_flags_binding_warnings_title")
                    description = props.polyglot.t("options_flags_binding_warnings_description")
                    selected = props.cliContext.isNoExtensibleBindingMessages()
                    onChange = {
                        props.updateCliContext(props.cliContext.setNoExtensibleBindingMessages(it))
                    }
                }
                styledDiv {
                    css {
                        +OptionsPageStyle.optionsDivider
                    }
                }
                checkboxWithDetails {
                    name = props.polyglot.t("options_flags_show_times_title")
                    description = props.polyglot.t("options_flags_show_times_description")
                    selected = props.cliContext.isShowTimes()
                    onChange = {
                        props.cliContext.setShowTimes(it)
                        props.updateCliContext(props.cliContext)
                    }
                }
                styledDiv {
                    css {
                        +OptionsPageStyle.optionsDivider
                    }
                }
                checkboxWithDetails {
                    name = props.polyglot.t("options_flags_allow_example_title")
                    description = props.polyglot.t("options_flags_allow_example_description")
                    selected = props.cliContext.isAllowExampleUrls()
                    onChange = {
                        props.cliContext.setAllowExampleUrls(it)
                        props.updateCliContext(props.cliContext)
                    }
                }
            }
            heading {
                text = props.polyglot.t("options_fhir_title")
            }
            styledDiv {
                css {
                    +OptionsPageStyle.optionsSubSection
                }
                dropdownWithExplanation {
                    defaultLabel = props.polyglot.t("options_default_label")
                    explanation = props.polyglot.t("options_fhir_description")
                    itemList = state.fhirVersionsList
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
            }
            heading {
                text = props.polyglot.t("options_ig_title")
            }
            styledDiv {
                css {
                    +OptionsPageStyle.optionsSubSection
                }
                igSelector {
                    polyglot = props.polyglot
                    fhirVersion = props.cliContext.getTargetVer()
                    igList = state.igList
                    igPackageNameList = state.igPackageNameList
                    onUpdatePackageName = {igPackageName, selected ->
                        setState {
                            igPackageNameList = state.igPackageNameList.map{ Pair(it.first, it.first == igPackageName)}.toMutableList()
                        }
                    }
                    selectedIgSet = props.selectedIgPackageInfo.toMutableSet()
                    onUpdateIg = { igPackageInfo, selected ->
                        val newSelectedIgSet = if (selected) {
                            selectedIgSet.plus(igPackageInfo).toMutableSet()
                        } else {
                            selectedIgSet.minus(igPackageInfo).toMutableSet()
                        }

                        props.updateSelectedIgPackageInfo(newSelectedIgSet)
                    }
                    onFilterStringChange = { partialIgName ->
                        mainScope.launch {
                            val igResponse = sendIGsRequest(partialIgName)
                            setState {
                                igList = igResponse.packageInfo
                                igPackageNameList = getPackageNames(igResponse.packageInfo)
                            }
                        }
                    }
                }
            }
            heading {
                text = props.polyglot.t("options_settings_title")
            }
            styledDiv {
                css {
                    +OptionsPageStyle.optionsSubSection
                }

                dropdownWithExplanation {
                    defaultLabel = props.polyglot.t("options_default_label")
                    explanation = props.polyglot.t("options_settings_snomed_description")
                    itemList = state.snomedVersionList
                    heading = props.polyglot.t("options_settings_snomed_title")
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
                    buttonLabel = props.polyglot.t("options_settings_tm_verify")
                    currentEntry = props.cliContext.getTxServer()
                    explanation = props.polyglot.t("options_settings_tm_description")
                    heading = props.polyglot.t("options_settings_tm_title")
                    onSubmitEntry = { url ->
                        GlobalScope.async {
                            val txServerOutcome = async { checkTxServer(url) }
                            if (txServerOutcome.await()) {
                                props.updateCliContext(props.cliContext.setTxServer(url))
                                true
                            } else {
                                false
                            }
                        }
                    }

                    successMessage = props.polyglot.t("options_settings_tm_success")
                    errorMessage = props.polyglot.t("options_settings_tm_error")
                }
            }
        }
    }

    private fun getPackageNames(packageInfo : List<PackageInfo>) : MutableList<Pair<String, Boolean>> {
        return packageInfo.map { Pair(it.id!!, false)}.toMutableList()
    }

    private suspend fun checkTxServer(txUrl: String): Boolean {
        var validTxServer = false
        try {
            withTimeout(TERMINOLOGY_CHECK_TIME_LIMIT) {
                val response = validateTxServer(txUrl)
                if (response.validTxServer) {
                    props.updateCliContext(props.cliContext.setTxServer(response.url))
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
fun RBuilder.optionsPage(handler: OptionsPageProps.() -> Unit) {
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