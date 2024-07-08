package ui.components.footer

import Polyglot
import api.sendValidatorVersionRequest
import css.const.BORDER_GRAY
import css.const.WHITE
import css.text.TextStyle
import kotlinx.coroutines.launch
import kotlinx.css.*
import mainScope

import react.*
import styled.*

import utils.Language

external interface FooterProps : Props {
    var language: Language //TODO
    var polyglot: Polyglot //TODO
}

class FooterState : State {
    var validatorVersion = "Unknown"
    var wrapperVersion = "Unknown"
}

class Footer : RComponent<FooterProps, FooterState>() {

    init {
        state = FooterState()
        mainScope.launch {
            val validatorVersionResponse = sendValidatorVersionRequest()
            println(validatorVersionResponse)
            setState {
                validatorVersion = validatorVersionResponse.coreVersion
                wrapperVersion = validatorVersionResponse.wrapperVersion
            }
        }
    }

    override fun RBuilder.render() {
        styledFooter {
            css {
                +FooterStyle.footerContainer
            }
            styledDiv {
                css {
                    +FooterStyle.footerColumn
                }
                styledSpan {
                    css {
                        +FooterStyle.footerTitleLarge
                    }
                    +"validator-wrapper"
                }
                styledSpan {
                    css {
                        +FooterStyle.footerTitleSmall
                    }
                    + state.wrapperVersion
                }
                styledSpan {
                    css {
                        +FooterStyle.footerTitleSmall
                    }
                    + (props.polyglot.t("footer_running_version") + state.validatorVersion)
                }
            }
            styledDiv {
                css {
                    +FooterStyle.footerColumn
                }
                footerLineItem {
                    href = "https://github.com/hapifhir/org.hl7.fhir.validator-wrapper"
                    icon = "images/github_white.png"
                    label = props.polyglot.t("footer_github")
                }
                footerLineItem {
                    href = "https://github.com/hapifhir/org.hl7.fhir.validator-wrapper/issues/new/choose"
                    icon = "images/bug_report_white.png"
                    label = props.polyglot.t("footer_create_issue")
                }
                footerLineItem {
                    href =
                        "https://github.com/hapifhir/org.hl7.fhir.validator-wrapper/releases/latest/download/validator_cli.jar"
                    icon = "images/download_white.png"
                    label = props.polyglot.t("footer_download_cli")
                }
            }
            styledDiv {
                css {
                    +FooterStyle.footerColumn
                }
                footerLineItem {
                    href = "https://confluence.hl7.org/display/FHIR/Using+the+FHIR+Validator"
                    icon = "images/documentation_white.png"
                    label = props.polyglot.t("footer_doc")
                }
                footerLineItem {
                    href = "../swagger-ui/index.html"
                    icon = "images/documentation_white.png"
                    label = props.polyglot.t("footer_openapi")
                }
            }
        }
    }
}

/**
 * Convenience method for instantiating the component.
 */
fun RBuilder.footer(handler: FooterProps.() -> Unit) {
    return child(Footer::class) {
        this.attrs(handler)
    }
}

/**
 * CSS
 */
object FooterStyle : StyleSheet("FooterStyle", isStatic = true) {
    private val FOOTER_HEIGHT = 200.px
    val footerContainer by css {
        display = Display.flex
        flexDirection = FlexDirection.row
        width = 100.pct
        height = FOOTER_HEIGHT
        backgroundColor = BORDER_GRAY
    }
    val footerColumn by css {
        width = 33.pc
        padding(all = 32.px)
        display = Display.flex
        flexDirection = FlexDirection.column
    }
    val footerTitleLarge by css {
        fontFamily = TextStyle.FONT_FAMILY_MAIN
        fontSize = 16.pt
        fontWeight = FontWeight.w800
        color = WHITE
    }
    val footerTitleSmall by css {
        fontFamily = TextStyle.FONT_FAMILY_MAIN
        fontSize = 12.pt
        fontWeight = FontWeight.w400
        color = WHITE
    }
}