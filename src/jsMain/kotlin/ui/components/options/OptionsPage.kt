package ui.components.options

import Polyglot
import api.sendIGsRequest
import api.sendVersionsRequest
import api.validateTxServer
import constants.Snomed
import context.LocalizationContext
import context.ValidationContext
import css.const.BORDER_GRAY
import css.const.HIGHLIGHT_GRAY
import kotlinx.coroutines.*
import kotlinx.css.*
import kotlinx.css.properties.border
import mainScope
import model.BundleValidationRule
import model.PackageInfo
import react.*
import styled.StyleSheet
import styled.css
import styled.styledDiv
import ui.components.options.menu.checkboxWithDetails
import ui.components.options.menu.dropdownWithExplanation
import ui.components.options.menu.textEntryField
import ui.components.tabs.heading

private const val TERMINOLOGY_CHECK_TIME_LIMIT = 20000L

external interface OptionsPageProps : Props {}

class OptionsPageState : State {
    var igList = mutableListOf<PackageInfo>()
    var igPackageNameList = mutableListOf<Pair<String, Boolean>>()
    var fhirVersionsList = mutableListOf<Pair<String, Boolean>>()
    var snomedVersionList = mutableListOf<Pair<String, Boolean>>()
    var initialized = false
}

class OptionsPage : RComponent<OptionsPageProps, OptionsPageState>() {

    init {
        state = OptionsPageState()
    }


    override fun RBuilder.render() {
        LocalizationContext.Consumer { localizationContext ->
            ValidationContext.Consumer { ctx ->
                val polyglot = localizationContext?.polyglot ?: Polyglot()
                val validationContext = ctx?.validationContext
                    ?: model.ValidationContext().setBaseEngine("DEFAULT")
                val igPackageInfoSet = ctx?.igPackageInfoSet ?: emptySet()

                // Initialize data on first render
                if (!state.initialized) {
                    mainScope.launch {
                        val igResponse = sendIGsRequest()
                        val versionsResponse = sendVersionsRequest()
                        setState {
                            igList = igResponse.packageInfo
                            igPackageNameList = getPackageNames(igResponse.packageInfo)
                            fhirVersionsList = versionsResponse.versions
                                .map { Pair(it, validationContext.getSv() == it) }
                                .toMutableList()
                            snomedVersionList = Snomed.values()
                                .map { Pair("${it.name} - ${it.code}", validationContext.getSnomedCTCode() == it.code) }
                                .toMutableList()
                            initialized = true
                        }
                    }
                }

                styledDiv {
                    css {
                        +OptionsPageStyle.optionsContainer
                    }
                    heading {
                        text = polyglot.t("options_flags_title")
                    }
                    styledDiv {
                        css {
                            +OptionsPageStyle.optionsSubSection
                        }
                        checkboxWithDetails {
                            name = polyglot.t("options_flags_do_native_title")
                            description = polyglot.t("options_flags_do_native_description")
                            selected = validationContext.isDoNative()
                            hasDescription = true
                            onChange = {
                                ctx?.updateValidationContext?.invoke(validationContext.setDoNative(it), true)
                                ctx?.setSessionId?.invoke("")
                            }
                        }
                        styledDiv {
                            css {
                                +OptionsPageStyle.optionsDivider
                            }
                        }
                        checkboxWithDetails {
                            name = polyglot.t("options_flags_must_support_title")
                            description = polyglot.t("options_flags_must_support_description")
                            selected = validationContext.isHintAboutNonMustSupport()
                            hasDescription = true
                            onChange = {
                                ctx?.updateValidationContext?.invoke(validationContext.setHintAboutNonMustSupport(it), true)
                                ctx?.setSessionId?.invoke("")
                            }
                        }
                        styledDiv {
                            css {
                                +OptionsPageStyle.optionsDivider
                            }
                        }
                        checkboxWithDetails {
                            name = polyglot.t("options_flags_valid_reference_title")
                            description = polyglot.t("options_flags_valid_reference_description")
                            selected = validationContext.isAssumeValidRestReferences()
                            hasDescription = true
                            onChange = {
                                ctx?.updateValidationContext?.invoke(validationContext.setAssumeValidRestReferences(it), true)
                                ctx?.setSessionId?.invoke("")
                            }
                        }
                        styledDiv {
                            css {
                                +OptionsPageStyle.optionsDivider
                            }
                        }
                        checkboxWithDetails {
                            name = polyglot.t("options_flags_binding_warnings_title")
                            description = polyglot.t("options_flags_binding_warnings_description")
                            selected = validationContext.isNoExtensibleBindingMessages()
                            hasDescription = true
                            onChange = {
                                ctx?.updateValidationContext?.invoke(validationContext.setNoExtensibleBindingMessages(it), true)
                                ctx?.setSessionId?.invoke("")
                            }
                        }
                        styledDiv {
                            css {
                                +OptionsPageStyle.optionsDivider
                            }
                        }
                        checkboxWithDetails {
                            name = polyglot.t("options_flags_show_times_title")
                            description = polyglot.t("options_flags_show_times_description")
                            selected = validationContext.isShowTimes()
                            hasDescription = true
                            onChange = {
                                validationContext.setShowTimes(it)
                                ctx?.updateValidationContext?.invoke(validationContext, true)
                                ctx?.setSessionId?.invoke("")
                            }
                        }
                        styledDiv {
                            css {
                                +OptionsPageStyle.optionsDivider
                            }
                        }
                        checkboxWithDetails {
                            name = polyglot.t("options_flags_allow_example_title")
                            description = polyglot.t("options_flags_allow_example_description")
                            selected = validationContext.isAllowExampleUrls()
                            hasDescription = true
                            onChange = {
                                validationContext.setAllowExampleUrls(it)
                                ctx?.updateValidationContext?.invoke(validationContext, true)
                                ctx?.setSessionId?.invoke("")
                            }
                        }
                        styledDiv {
                            css {
                                +OptionsPageStyle.optionsDivider
                            }
                        }
                        checkboxWithDetails {
                            name = polyglot.t("options_flags_check_ips_codes_title")
                            description = polyglot.t("options_flags_check_ips_codes_description")
                            selected = validationContext.isCheckIPSCodes()
                            hasDescription = true
                            onChange = {
                                validationContext.setCheckIPSCodes(it)
                                ctx?.updateValidationContext?.invoke(validationContext, true)
                                ctx?.setSessionId?.invoke("")
                            }
                        }
                    }
                    heading {
                        text = polyglot.t("options_fhir_title")
                    }
                    styledDiv {
                        css {
                            +OptionsPageStyle.optionsSubSection
                        }
                        dropdownWithExplanation {
                            defaultLabel = polyglot.t("options_default_label")
                            explanation = polyglot.t("options_fhir_description")
                            itemList = state.fhirVersionsList
                            onItemSelected = { version ->
                                setState {
                                    validationContext.setTargetVer(version)
                                    validationContext.setSv(version)
                                    state.fhirVersionsList.forEach {
                                        fhirVersionsList[fhirVersionsList.indexOf(it)] =
                                            when (it.first) {
                                                version -> it.copy(second = true)
                                                else -> it.copy(second = false)
                                            }
                                    }
                                }
                                ctx?.updateValidationContext?.invoke(validationContext, true)
                                ctx?.setSessionId?.invoke("")
                            }
                        }
                    }
                    heading {
                        text = polyglot.t("options_ig_title")
                    }
                    styledDiv {
                        css {
                            +OptionsPageStyle.optionsSubSection
                        }
                        igSelector {
                            fhirVersion = validationContext.getSv()
                            igList = state.igList
                            igPackageNameList = state.igPackageNameList
                            onUpdatePackageName = {igPackageName, selected ->
                                setState {
                                    igPackageNameList = state.igPackageNameList.map{ Pair(it.first, it.first == igPackageName)}.toMutableList()
                                }
                            }
                            selectedIgSet = igPackageInfoSet.toMutableSet()
                            onUpdateIg = { igPackageInfo, selected ->
                                val newSelectedIgSet = if (selected) {
                                    selectedIgSet.plus(igPackageInfo).toMutableSet()
                                } else {
                                    selectedIgSet.minus(igPackageInfo).toMutableSet()
                                }
                                ctx?.updateIgPackageInfoSet?.invoke(newSelectedIgSet, true)
                                ctx?.setSessionId?.invoke("")
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
                        text = polyglot.t("options_profiles_title")
                    }
                    styledDiv {
                        css {
                            +OptionsPageStyle.optionsSubSection
                        }
                        addProfile {}
                    }
                    heading {
                        text = polyglot.t("options_extensions_title")
                    }
                    styledDiv {
                        css {
                            +OptionsPageStyle.optionsSubSection
                        }
                        addExtension {}
                    }
                    heading {
                        text = polyglot.t("options_bundle_validation_rules_title")
                    }
                    styledDiv {
                        css {
                            +OptionsPageStyle.optionsSubSection
                        }
                        addBundleValidationRule {}
                    }
                    heading {
                        text = polyglot.t("options_settings_title")
                    }
                    styledDiv {
                        css {
                            +OptionsPageStyle.optionsSubSection
                        }

                        dropdownWithExplanation {
                            defaultLabel = polyglot.t("options_default_label")
                            explanation = polyglot.t("options_settings_snomed_description")
                            itemList = state.snomedVersionList
                            heading = polyglot.t("options_settings_snomed_title")
                            onItemSelected = { version ->
                                setState {
                                    validationContext.setSnomedCT(version.replace("[^0-9]".toRegex(), ""))
                                    state.snomedVersionList.forEach {
                                        snomedVersionList[snomedVersionList.indexOf(it)] =
                                            when (it.first) {
                                                version -> it.copy(second = true)
                                                else -> it.copy(second = false)
                                            }
                                    }
                                }
                                ctx?.updateValidationContext?.invoke(validationContext, true)
                                ctx?.setSessionId?.invoke("")
                            }
                        }
                        styledDiv {
                            css {
                                +OptionsPageStyle.otherSettingsDivider
                            }
                        }
                        textEntryField {
                            buttonLabel = polyglot.t("options_settings_tm_verify")
                            currentEntry = validationContext.getTxServer()
                            explanation = polyglot.t("options_settings_tm_description")
                            heading = polyglot.t("options_settings_tm_title")
                            textFieldId = "terminology_server_entry"
                            onSubmitEntry = { url ->
                                GlobalScope.async {
                                    val txServerOutcome = async { checkTxServer(url, validationContext, ctx) }
                                    if (txServerOutcome.await()) {
                                        true
                                    } else {
                                        false
                                    }
                                }
                            }

                            successMessage = polyglot.t("options_settings_tm_success")
                            errorMessage = polyglot.t("options_settings_tm_error")
                        }
                    }
                }
            }
        }
    }



    private fun getPackageNames(packageInfo : List<PackageInfo>) : MutableList<Pair<String, Boolean>> {
        return packageInfo.map { Pair(it.id!!, false)}.toMutableList()
    }

    private suspend fun checkTxServer(
        txUrl: String,
        validationContext: model.ValidationContext,
        ctx: context.ValidationContextValue?
    ): Boolean {
        var validTxServer = false
        try {
            withTimeout(TERMINOLOGY_CHECK_TIME_LIMIT) {
                val response = validateTxServer(txUrl)
                if (response.validTxServer) {
                    ctx?.updateValidationContext?.invoke(validationContext.setTxServer(response.url), true)
                    ctx?.setSessionId?.invoke("")
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