package css

import css.const.*
import kotlinx.css.*
import styled.injectGlobal

const val ACE_EDITOR_INFO = "ace-editor-info"
const val ACE_EDITOR_WARNING = "ace-editor-warning"
const val ACE_EDITOR_ERROR = "ace-editor-error"
const val ACE_EDITOR_FATAL = "ace-editor-fatal"
const val ACE_EDITOR_DEFAULT = "ace-editor-default"

const val ACE_EDITOR_SELECTED = "ace-editor-selected"
const val ACE_EDITOR_HIGHLIGHT = "ace-editor-highlight"

const val ACE_TOOLTIP = "ace_tooltip"

/**
 * By default, we want to set the global styles for the entire project to 0 px margin and padding.
 */
object GlobalStyles {
    fun inject() {
        val styles = CSSBuilder(allowClasses = true).apply {
            body {
                margin(0.px)
                padding(0.px)
            }
            ".${ACE_EDITOR_HIGHLIGHT}" {
                opacity = 0.25
                position = Position.absolute;
            }
            ".${ACE_EDITOR_SELECTED}" {
                opacity = 0.5
            }
            ".${ACE_EDITOR_INFO}" {
                backgroundColor = INFO_BLUE;
            }
            ".${ACE_EDITOR_WARNING}" {
                backgroundColor = WARNING_YELLOW;
            }
            ".${ACE_EDITOR_ERROR}" {
                backgroundColor = ERROR_ORANGE;
            }
            ".${ACE_EDITOR_FATAL}" {
                backgroundColor = FATAL_PINK;
            }
            ".${ACE_EDITOR_DEFAULT}" {
                backgroundColor = BORDER_GRAY;
            }
            ".${ACE_TOOLTIP}" {
                maxWidth = 500.px
                wordWrap =  WordWrap.breakWord //WordWrap.valueOf("break-word !important")
                whiteSpace = WhiteSpace.preWrap
            }
        }
        println("Style")
        println(styles.toString())
        injectGlobal(styles.toString())
    }
}

// TODO I'm leaving this here, so I remember how to do it later.
//        ancestorHover(".${ContextSettingsStyle.name}-${ContextSettingsStyle::dropdown.name}") {
//            display = Display.block
//        }