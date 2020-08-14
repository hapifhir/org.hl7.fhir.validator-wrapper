package uicomponents

import css.FileSummaryStyle
import css.TextStyle
import io.ktor.util.escapeHTML
import kotlinx.css.*
import kotlinx.html.*
import kotlinx.html.attributes.enumEncode
import kotlinx.html.js.*
import org.w3c.dom.HTMLDivElement
import react.*
import react.dom.*

import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLTextAreaElement
import org.w3c.dom.asList
import org.w3c.files.File
import org.w3c.files.FileReader
import react.RProps
import react.dom.textArea
import styled.*
import kotlin.browser.document
import kotlin.browser.window

/**
 * We need a way to provide a callback to the main page using this component. This can be done, through React props.
 * In this case, we define the callback for the Submit funtionality here.
 */
external interface FileSummaryProps : RProps {
    var file: File
    var active: Boolean
    var onClose: () -> Unit
}

class FileSummaryComponent : RComponent<FileSummaryProps, RState>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                +FileSummaryStyle.overlay
                display = if (props.active) Display.block else Display.none
            }
            styledDiv {
                css {
                    +FileSummaryStyle.modalContent
                }
                styledDiv {
                    css {
                        +FileSummaryStyle.titleBar
                    }
                    styledP {
                        css {
                            +TextStyle.h2
                            +FileSummaryStyle.centerVertical
                        }
                        +"this_is_the_file_title.json"
                    }
                    styledImg {
                        css {
                            +FileSummaryStyle.closeButton
                            +FileSummaryStyle.closeButtonHover
                        }
                        attrs {
                            src = "highlight_off-24px.svg"
                            onClickFunction = {
                                props.onClose()
                            }
                        }
                    }
                }
                styledHr {
                    css {
                        +FileSummaryStyle.horizontalRule
                    }
                }

                styledDiv {
                    css {
                        +FileSummaryStyle.fileContent
                    }
                    +"Unlike flying or astral projection, walking through walls is a totally earth-related craft, but a lot more interesting than pot making or driftwood lamps. I got started at a picnic up in Bowstring in the northern part of the state. A fellow walked through a brick wall right there in the park. I said, 'Say, I want to try that.' Stone walls are best, then brick and wood. Wooden walls with fiberglass insulation and steel doors aren't so good. They won't hurt you. If your wall walking is done properly, both you and the wall are left intact. It is just that they aren't pleasant somehow. The worst things are wire fences, maybe it's the molecular structure of the alloy or just the amount of give in a fence, I don't know, but I've torn my jacket and lost my hat in a lot of fences. The best approach to a wall is, first, two hands placed flat against the surface; it's a matter of concentration and just the right pressure. You will feel the dry, cool inner wall with your fingers, then there is a moment of total darkness before you step through on the other side."
                }

            }
        }
    }
}

/**
 * We can use lambdas with receivers to make the component easier to work with.
 */
fun RBuilder.fileSummaryComponent(handler: FileSummaryProps.() -> Unit): ReactElement {
    return child(FileSummaryComponent::class) {
        this.attrs(handler)
    }
}
