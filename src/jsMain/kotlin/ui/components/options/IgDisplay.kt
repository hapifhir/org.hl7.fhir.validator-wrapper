package ui.components.options

import Polyglot
import css.const.*
import css.text.TextStyle
import kotlinx.css.*
import kotlinx.css.properties.border
import kotlinx.html.js.onClickFunction
import kotlinx.html.title
import model.PackageInfo
import react.*
import react.dom.attrs
import styled.*

external interface IgDisplayProps : Props {
    var fhirVersion: String
    var packageInfo: PackageInfo
    var polyglot: Polyglot
    var onDelete: () -> Unit
}

class IgDisplay : RComponent<IgDisplayProps, State>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                +IgDisplayStyle.mainDiv
                if (!props.packageInfo.fhirVersionMatches(props.fhirVersion)) {
                    background = "repeating-linear-gradient(\n" +
                            "  45deg,\n" +
                            "  ${WHITE},\n" +
                            "  ${WHITE} 10px,\n" +
                            "  ${FATAL_PINK.changeAlpha(0.2)} 10px,\n" +
                            "  ${FATAL_PINK.changeAlpha(0.2)} 20px\n" +
                            ");"
                }
            }
            if (!props.packageInfo.fhirVersionMatches(props.fhirVersion)) {
                attrs {
                    title = props.polyglot.t("options_ig_not_supported") + " ${props.fhirVersion}"
                }
            }
            styledSpan {
                css {
                    +TextStyle.dropDownLabel
                    +IgDisplayStyle.igName
                }
                +PackageInfo.igLookupString(props.packageInfo)
            }
            styledImg {
                css {
                    +IgDisplayStyle.closeButton
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
fun RBuilder.igDisplay(handler: IgDisplayProps.() -> Unit) {
    return child(IgDisplay::class) {
        this.attrs(handler)
    }
}

/**
 * CSS
 */
object IgDisplayStyle : StyleSheet("IgDisplayStyle", isStatic = true) {
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
