package reactredux.slices

import Polyglot
import reactredux.store.RThunk
import reactredux.thunk.FetchPolyglotThunk
import redux.RAction
import utils.Language

object LocalizationSlice {


    fun getPolyglot ()  : Polyglot {
        console.log("getPolyglot")
        var polyglot = Polyglot(js("{locale: \"en\"}"))
        polyglot.extend(phrases = js("{\n" +
                "  \"validate_heading\" : \"Validate\",\n" +
                "  \"appscreen_validator_major\" : \"Validate Resources\",\n" +
                "  \"appscreen_validator_minor\" : \"Manually enter, or upload resources for validation.\",\n" +
                "  \"enter_resources_heading\" : \"Enter Resource\",\n" +
                "  \"manual_entry_place_holder\" : \"Enter Resource Manually...\",\n" +
                "  \"manual_entry_title\" : \"Code\",\n" +
                "  \"manual_entry_error\" : \"Please enter something to validate.\",\n" +
                "  \"manual_entry_exception\" : \"Cannot parse entered text as valid JSON/XML.\",\n" +
                "  \"validate_button\" : \"Validate\",\n" +
                "  \"upload_resources_heading\" : \"Upload Resources\",\n" +
                "  \"upload_files_title\" : \"Uploaded Files\",\n" +
                "  \"upload_button\" : \"Upload\",\n" +
                "  \"upload_entry_view\" : \"View\",\n" +
                "  \"upload_entry_delete\" : \"Delete\",\n" +
                "  \"options_heading\" : \"Options\",\n" +
                "  \"appscreen_settings_major\" : \"Validation Options\",\n" +
                "  \"appscreen_settings_minor\" : \"Modify setting for validating resources.\",\n" +
                "  \"options_flags_title\" : \"Flags\",\n" +
                "  \"options_flags_1_title\" : \"Native Validation (doNative)\",\n" +
                "  \"options_flags_1_description\" : \"By default, the validation engine only validates using the FHIR structures and profiles. The publication processes also generate a variety of xml, json and RDF schemas. You can ask the validator to validate against these as well using this option. Note that there is nothing in these schemas that is not validated directly by the engine itself anyway, so the main use for this is to see the kind of errors that would be reported from these schemas by other software.\",\n" +
                "  \"options_flags_2_title\" : \"Must Support (hintAboutMustSupport)\",\n" +
                "  \"options_flags_2_description\" : \"In some cases (e.g. when creating examples for implementation guides or when checking for potential interoperability issues with a new communication partner), it can be useful to know when data elements are present in an instance when those elements are not \\\"mustSupport\\\" in the profile(s) the instance is being validated against. Identifying situations where this occurs might drive a change to the profile or cause a designer to drop an element from the instance. In other cases, the presence of the element can be fine and the information message ignored.\",\n" +
                "  \"options_flags_3_title\" : \"Assume Valid Rest References (assumeValidRestReferences)\",\n" +
                "  \"options_flags_3_description\" : \"If the validator can't fetch target resources, it can at least be instructed validate that the target resource is correct. For instance, if the allowed target types are (Patient or RelatedPerson), and the reference is Group/12345, then this would be usually be an error. However this is not actually explicitly stated in the FHIR specification - a reference could be to http://some.server/somewhere - it doesn't need to look like a valid FHIR RESTful reference. In fact, it's not an error even if it happens to. So by default, the validator can assume nothing about the type of resource from the content of the reference itself. However most implementers do follow those rules - a reference to either Group/12345 or http://some-server/somwhere/Group/12345 is definitely to a Group resource. (and it's definitely recommended to follow these rules). The flag assumeValidRestReferences instructs the validator to use the type found in references that look like valid RESTful URLs when validating the type of the reference.\",\n" +
                "  \"options_flags_4_title\" : \"Extensible Binding Warnings (noExtensibleBindingWarnings)\",\n" +
                "  \"options_flags_4_description\" : \"When the validator encounters a code that is not part of an extensible binding, it adds a warning to suggest that the code be reviewed. The validator can't determine whether the meaning of the code makes it an inappropriate extension, or not; this requires human review. Hence, the warning. But the code may be valid - that's why extensible is defined - so in some operational uses of the validator, it is appropriate to turn these warnings off\",\n" +
                "  \"options_flags_5_title\" : \"Show Times (showTimes)\",\n" +
                "  \"options_flags_5_description\" : \"When the validator encounters a code that is not part of an extensible binding, it adds a warning to suggest that the code be reviewed. The validator can't determine whether the meaning of the code makes it an inappropriate extension, or not; this requires human review. Hence, the warning. But the code may be valid - that's why extensible is defined - so in some operational uses of the validator, it is appropriate to turn these warnings off\",\n" +
                "  \"options_flags_6_title\" : \"Allow Example URLs (allow-example-urls)\",\n" +
                "  \"options_flags_6_description\" : \"Some of the examples in the FHIR specification have URLs in them that refer to example.org. By default, the validator will always mark any such references as an error, but this can be overridden with this parameter.\",\n" +
                "  \"options_fhir_title\" : \"FHIR version\",\n" +
                "  \"options_fhir_description\" : \"The validator checks the resource against the base specification. By default, this is specification version 4.0.1.\",\n" +
                "  \"options_ig_title\" : \"Implementation Guides\",\n" +
                "  \"options_ig_description_1\" : \"You can validate against one or more published implementation guides. Select IGs using the dropdown menus below and click the \",\n" +
                "  \"options_ig_description_2\" : \"Add\",\n" +
                "  \"options_ig_description_3\" : \" button to include them in your validation.\",\n" +
                "  \"options_ig_dropdown\" : \"Select IG\",\n" +
                "  \"options_ig_dropdown_hint\" : \"Search IGS...\",\n" +
                "  \"options_ig_version_dropdown_default\" : \"No Compatible versions\",\n" +
                "  \"options_ig_version_dropdown_hint\" : \"Search IG version\",\n" +
                "  \"options_ig_add\" : \"Add\",\n" +
                "  \"options_ig_selected\" : \"Selected\",\n" +
                "  \"options_ig_not_supported\" : \"IG not supported for FHIR version\",\n" +
                "  \"options_settings_title\" : \"Other Settings\",\n" +
                "  \"options_settings_snomed_title\" : \"Select SNOMED Version\",\n" +
                "  \"options_settings_snomed_description\" : \"You can specify which edition of SNOMED CT for the terminology server to use when doing SNOMED CT Validation.\",\n" +
                "  \"options_settings_tm_title\" : \"Set Terminology Server\",\n" +
                "  \"options_settings_tm_description\" : \"The validation engine uses a terminology server to validate codes from large external terminologies such as SNOMED CT, LOINC, RxNorm, etc. By default, the terminology server used is tx.fhir.org, which supports most of these terminologies. If you want to use another terminology server, you can specify one here. As a warning, the server will check that the CapabilityStatement of the provided server is set correctly.\",\n" +
                "  \"options_settings_verify\" : \"Verify\",\n" +
                "  \"footer_title\" : \"validator-wrapper\",\n" +
                "  \"footer_running\" : \"running validator v\",\n" +
                "  \"footer_github\" : \"view project on github\",\n" +
                "  \"footer_issue\" : \"log an issue with the team\",\n" +
                "  \"footer_cli\" : \"download the latest cli\",\n" +
                "  \"footer_doc\" : \"view the validator documentation\",\n" +
                "  \"validation_results\" : \"Results\",\n" +
                "  \"validation_fatals\" : \"Fatals\",\n" +
                "  \"validation_errors\" : \"Errors\",\n" +
                "  \"validation_warnings\" : \"Warnings\",\n" +
                "  \"validation_info\" : \"Information\"\n" +
                "}"))
        return polyglot
    }

    data class State(
        val polyglotInstance: Polyglot = getPolyglot(),
        val selectedLangauge: Language = Language.US_ENGLISH,
    )

    fun fetchPolyglot(localeString: String): RThunk {
        return FetchPolyglotThunk(localeString)
    }

    data class SetPolyglot(val polyglotInstance: Polyglot) : RAction
    data class SetLanguage(val selectedLangauge: Language) : RAction

    fun reducer(state: State = State(), action: RAction): State {
        return when (action) {
            is SetPolyglot -> {
                println("setting new polyglot instance\nexisting -> ${state.polyglotInstance.t("heading_validate")}")
                println("setting new polyglot instance\nnew -> ${action.polyglotInstance.t("heading_validate")}")
                state.copy(polyglotInstance = action.polyglotInstance)
            }
            is SetLanguage -> {
                println("setting new lang instance\nexisting -> ${state.selectedLangauge}")
                println("setting new lang instance\nnew -> ${action.selectedLangauge}")
                state.copy(selectedLangauge = action.selectedLangauge)
            }
            else -> state
        }
    }
}