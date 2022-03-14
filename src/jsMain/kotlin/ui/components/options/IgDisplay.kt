package ui.components.options

import css.const.*
import css.text.TextStyle
import kotlinx.css.*
import kotlinx.css.properties.border
import kotlinx.html.js.onClickFunction
import kotlinx.html.title
import model.PackageInfo
import react.*
import styled.*

external interface IgDisplayProps : RProps {
    var fhirVersion: String
    var packageInfo: PackageInfo
    var onDelete: () -> Unit
}

class IgDisplay : RComponent<IgDisplayProps, RState>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                +IgUrlDisplayStyle.mainDiv
                if (!props.packageInfo.fhirVersionMatches(props.fhirVersion)) {
                    background = "repeating-linear-gradient(\n" +
                            "  45deg,\n" +
                            "  ${WHITE},\n" +
                            "  ${WHITE} 10px,\n" +
                            "  ${FATAL_PINK.changeAlpha(0.2)} 10px,\n" +
                            "  ${FATAL_PINK.changeAlpha(0.2)} 20px\n" +
                            ");"
                }
                borderColor = HL7_RED
            }
            if (!props.packageInfo.fhirVersionMatches(props.fhirVersion)) {
                attrs {
                    title = "IG not supported for FHIR version ${props.fhirVersion}"
                }
            }
            styledSpan {
                css {
                    +TextStyle.dropDownLabel
                    +IgUrlDisplayStyle.igName
                }
                +PackageInfo.igLookupString(props.packageInfo)
            }
            styledImg {
                css {
                    +IgUrlDisplayStyle.closeButton
                }
                attrs {
                    src = "images/close_black.png"
                    onClickFunction = {
                        props.onDelete()
                    }
                }
            }
        }
    }
}

/**
 * React Component Builder
 */
fun RBuilder.igDisplay(handler: IgDisplayProps.() -> Unit): ReactElement {
    return child(IgDisplay::class) {
        this.attrs(handler)
    }
}

/**
 * CSS
 */
object IgUrlDisplayStyle : StyleSheet("IgUrlDisplayStyle", isStatic = true) {
    val mainDiv by css {
        display = Display.flex
        flexDirection = FlexDirection.row
        border(width = 1.px, style = BorderStyle.solid, color = BORDER_GRAY)
        margin(right = 16.px, top = 4.px, bottom = 4.px)
        padding(horizontal = 16.px, vertical = 8.px)
        backgroundColor = WHITE
    }
    val igName by css {
        padding(right = 16.px)
    }
    val closeButton by css {
        width = 16.px
        height = 16.px
        alignSelf = Align.center
        borderRadius = 50.pct
        hover {
            backgroundColor = HIGHLIGHT_GRAY
        }
        active {
            backgroundColor = INACTIVE_GRAY
        }
    }
}
