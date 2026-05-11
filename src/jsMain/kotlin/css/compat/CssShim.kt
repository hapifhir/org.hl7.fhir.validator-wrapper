// Created by claude-sonnet-4-6
// Restores extension functions removed from kotlinx.css in kotlin-wrappers pre.621.
// The pre.621 API replaced these functions with property setters using Padding/Margin/Flex data classes.
package kotlinx.css

// --- padding ---

fun StyledElement.padding(all: LinearDimension) {
    this.padding = Padding(all)
}

fun StyledElement.padding(
    vertical: LinearDimension? = null,
    horizontal: LinearDimension? = null,
) {
    this.padding = Padding(vertical = vertical, horizontal = horizontal)
}

fun StyledElement.padding(
    top: LinearDimension? = null,
    right: LinearDimension? = null,
    bottom: LinearDimension? = null,
    left: LinearDimension? = null,
) {
    this.padding = Padding(top = top, right = right, bottom = bottom, left = left)
}

// --- margin ---

fun StyledElement.margin(all: LinearDimension) {
    this.margin = Margin(all)
}

fun StyledElement.margin(
    vertical: LinearDimension? = null,
    horizontal: LinearDimension? = null,
) {
    this.margin = Margin(vertical = vertical, horizontal = horizontal)
}

fun StyledElement.margin(
    top: LinearDimension? = null,
    right: LinearDimension? = null,
    bottom: LinearDimension? = null,
    left: LinearDimension? = null,
) {
    this.margin = Margin(top = top, right = right, bottom = bottom, left = left)
}

// --- flex ---

fun StyledElement.flex(flexGrow: Number) {
    this.flex = Flex(flexGrow)
}

fun StyledElement.flex(flexBasis: LinearDimension) {
    this.flex = Flex(flexBasis)
}

fun StyledElement.flex(flexGrow: Number, flexShrink: Number) {
    this.flex = Flex(flexGrow, flexShrink)
}

fun StyledElement.flex(flexGrow: Number, flexBasis: LinearDimension) {
    this.flex = Flex(flexGrow, flexBasis)
}

fun StyledElement.flex(flexGrow: Number, flexShrink: Number, flexBasis: FlexBasis) {
    this.flex = Flex(flexGrow, flexShrink, flexBasis)
}

fun StyledElement.flex(flexGrow: Number, flexShrink: Number, flexBasis: LinearDimension) {
    this.flex = Flex(flexGrow, flexShrink, flexBasis)
}
