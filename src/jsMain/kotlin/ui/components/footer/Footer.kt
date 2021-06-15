package ui.components.footer

import Polyglot
import css.const.BORDER_GRAY
import css.const.HL7_RED
import css.const.WHITE
import css.text.TextStyle
import kotlinx.css.*
import model.AppScreen
import react.*
import styled.*
import utils.Language

external interface FooterProps : RProps {
    var appScreen: AppScreen
    var language: Language
    var polyglot: Polyglot
}

class Footer : RComponent<FooterProps, RState>() {

    override fun RBuilder.render() {
        styledFooter {
            css {
                +HeaderStyle.footerContainer

            }
            styledDiv {
                css {
                    width = 33.pc
                    padding(all = 32.px)
                    display = Display.flex
                    flexDirection = FlexDirection.column
                }
                styledSpan {
                    css {
                        fontFamily = TextStyle.FONT_FAMILY_MAIN
                        fontSize = 16.pt
                        fontWeight = FontWeight.w800
                        color = WHITE
                    }
                    +"validator-wrapper"
                }
                styledSpan {
                    css {
                        fontFamily = TextStyle.FONT_FAMILY_MAIN
                        fontSize = 12.pt
                        fontWeight = FontWeight.w400
                        color = WHITE
                    }
                    +"running validator v5.4.3"
                }
            }
            styledDiv {
                css {
                    width = 33.pc
                    padding(all = 32.px)
                    display = Display.flex
                    flexDirection = FlexDirection.column
                }
                footerLineItem {
                    href = "https://github.com/hapifhir/org.hl7.fhir.validator-wrapper"
                    icon = "images/github_white.png"
                    label = "view project on github"
                }
                footerLineItem {
                    href = "https://github.com/hapifhir/org.hl7.fhir.validator-wrapper/issues/new/choose"
                    icon = "images/bug_report_white.png"
                    label = "log an issue with the team"
                }
                footerLineItem {
                    href = "https://github.com/hapifhir/org.hl7.fhir.validator-wrapper/releases/latest/download/validator_cli.jar"
                    icon = "images/download_white.png"
                    label = "download the latest cli"
                }
            }
            styledDiv {
                css {
                    width = 33.pc
                    padding(all = 32.px)
                    display = Display.flex
                    flexDirection = FlexDirection.column
                }
                footerLineItem {
                    href = "https://confluence.hl7.org/display/FHIR/Using+the+FHIR+Validator"
                    icon = "images/documentation_white.png"
                    label = "view the validator documentation"
                }
            }
        }
    }
}

/**
 * Convenience method for instantiating the component.
 */
fun RBuilder.footer(handler: FooterProps.() -> Unit): ReactElement {
    return child(Footer::class) {
        this.attrs(handler)
    }
}

/**
 * CSS
 */
object HeaderStyle : StyleSheet("FooterStyle", isStatic = true) {
    private val FOOTER_HEIGHT = 200.px
    val footerContainer by css {
        display = Display.flex
        flexDirection = FlexDirection.row
        width = 100.pct
        height = FOOTER_HEIGHT
        backgroundColor = BORDER_GRAY
    }
}