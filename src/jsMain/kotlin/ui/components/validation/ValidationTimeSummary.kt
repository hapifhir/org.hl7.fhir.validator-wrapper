package ui.components.validation

import css.text.TextStyle
import kotlinx.css.FontWeight
import kotlinx.css.fontWeight
import kotlinx.css.padding
import kotlinx.css.px
import model.ValidationTime
import react.Props
import react.RBuilder
import react.RComponent
import react.State
import react.dom.div
import styled.StyleSheet
import styled.css
import styled.styledDiv
import styled.styledSpan

external interface ValidationTimeSummaryProps : Props {
    var validationTime: ValidationTime
}

private const val NANOS_TO_MILLIS = 1000000

class ValidationTimeSummary : RComponent<ValidationTimeSummaryProps, State>() {

    private fun getSummaryString(
        overall: Long,
        terminology: Long,
        structureDefinition: Long,
        resourceParse: Long,
        fhirPath: Long,
        checkingSpecials: Long
    ) : String {
        return "Overall: ${overall}ms Terminology: ${terminology}ms StructureDefinition: ${structureDefinition}ms Resource Parse: ${resourceParse}ms FHIRPath: ${fhirPath} Checking Specials: ${checkingSpecials}"
    }
    override fun RBuilder.render() {
        styledDiv {
            css {
                padding(16.px)
            }
            styledSpan {
                css {
                    +TextStyle.codeTextBase
                    fontWeight = FontWeight.w600
                }
                + "Validation Time Summary: "
            }
            styledSpan {
                css {
                    +TextStyle.codeTextBase
                }
            + getSummaryString(
                props.validationTime.getOverall() / NANOS_TO_MILLIS,
                props.validationTime.getTerminology() / NANOS_TO_MILLIS,
                props.validationTime.getStructureDefinition() / NANOS_TO_MILLIS,
                props.validationTime.getResourceParse() / NANOS_TO_MILLIS,
                props.validationTime.getFhirPath() / NANOS_TO_MILLIS,
               props.validationTime.getCheckingSpecials() / NANOS_TO_MILLIS
            )
            }
        }
    }
}

/**
 * React Component Builder
 */
fun RBuilder.validationTimeSummary(handler: ValidationTimeSummaryProps.() -> Unit) {
    return child(ValidationTimeSummary::class) {
        this.attrs(handler)
    }
}

/**
 * CSS
 */
object ValidationTimeSummaryStyle : StyleSheet("ValidationOutcomePopupHeaderStyle", isStatic = true) {

}