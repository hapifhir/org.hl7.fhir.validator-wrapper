package ui.components.validation

import Polyglot
import css.text.TextStyle
import kotlinx.css.*
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
import utils.getJS

external interface ValidationTimeSummaryProps : Props {
    var prefix: String?
    var validationTime: ValidationTime
    var polyglot: Polyglot
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
    ): String {
        return props.polyglot.t(
            "validation_time_summary",
            getJS(
                arrayOf(
                    Pair("overall", overall),
                    Pair("terminology", terminology),
                    Pair("structureDefinition", structureDefinition),
                    Pair("resourceParse", resourceParse),
                    Pair("fhirPath", fhirPath),
                    Pair("checkingSpecials", checkingSpecials)
                )
            )
        )
    }

    override fun RBuilder.render() {
        div {
            if (props.prefix != null) {
                styledSpan {
                    css {
                        +TextStyle.codeTextBase
                        fontSize = 10.pt
                        fontWeight = FontWeight.w600
                        paddingRight = 10.px
                    }
                    +props.prefix!!
                }
            }

            styledSpan {
                css {
                    fontSize = 10.pt
                    +TextStyle.codeTextBase
                }
                +getSummaryString(
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