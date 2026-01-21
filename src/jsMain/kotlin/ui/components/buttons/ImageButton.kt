package ui.components.buttons

import css.text.TextStyle
import kotlinx.css.*
import kotlinx.html.js.onClickFunction
import kotlinx.css.properties.border
import react.*
import react.dom.attrs
import styled.*

external interface ImageButtonProps : Props {
    var image: String?
    var borderColor: Color
    var backgroundColor: Color
    var label: String

    // Callback function when clicked
    var onSelected: () -> Unit
}

/**
 * A generic button with the option to customize, color, label, and optional icon
 */
class ImageButton : RComponent<ImageButtonProps, State>() {
    override fun RBuilder.render() {
        // main button layout
        styledDiv {
            css {
                +ImageButtonStyle.button
                border(width = 1.px, style = BorderStyle.solid, color = props.borderColor, borderRadius = 4.px)
                backgroundColor = props.backgroundColor
                hover {
                    backgroundColor = props.borderColor.changeAlpha(0.1)
                }
                active {
                    backgroundColor = props.borderColor
                }
            }
            attrs {
                onClickFunction = {
                     props.onSelected()
                }
            }
            // button label
            styledP {
                css {
                    +TextStyle.genericButtonLabel
                    if (!props.image.isNullOrEmpty()) {
                        // image has been defined, so we include right padding
                        margin(left = 0.px, top = 0.px, right = 8.px, bottom = 0.px)
                    }
                }
                +props.label
            }
            // button image
            if (!props.image.isNullOrEmpty()) {
                styledImg {
                    css {
                        +ImageButtonStyle.buttonImage
                    }
                    attrs {
                        src = props.image!!
                    }
                }
            }
        }
    }
}

/**
 * React Component Builder
 */
fun RBuilder.imageButton(handler: ImageButtonProps.() -> Unit){
    return child(ImageButton::class) {
        this.attrs(handler)
    }
}

/**
 * CSS
 */
object ImageButtonStyle : StyleSheet("ImageButtonStyle", isStatic = true) {
    val button by ImageButtonStyle.css {
        cursor = Cursor.pointer
        display = Display.inlineFlex
        flexDirection = FlexDirection.row
        minHeight = 36.px
        alignSelf = Align.center
        padding(horizontal = 16.px, vertical = 8.px)
    }
    val buttonImage by ImageButtonStyle.css {
        height = 16.px
        width = 16.px
        alignSelf = Align.center
    }
}
