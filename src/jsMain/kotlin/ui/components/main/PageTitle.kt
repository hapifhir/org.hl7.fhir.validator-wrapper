package ui.components.main

import css.component.page.PageTitleStyle
import css.text.TextStyle
import kotlinx.css.paddingBottom
import kotlinx.css.rem
import react.*
import styled.css
import styled.styledDiv
import styled.styledSpan

external interface PageTitleProps : RProps {
    var majorText: String
    var minorText: String
}

class PageTitle : RComponent<PageTitleProps, RState>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                +PageTitleStyle.sectionTitle
            }
            styledSpan {
                css {
                    +TextStyle.pageTitle
                    paddingBottom = 1.rem
                }
                +props.majorText
            }
            styledSpan {
                css {
                    +TextStyle.pageDetails
                }
                +props.minorText
            }
        }
    }
}

fun RBuilder.sectionTitle(handler: PageTitleProps.() -> Unit): ReactElement {
    return child(PageTitle::class) {
        this.attrs(handler)
    }
}