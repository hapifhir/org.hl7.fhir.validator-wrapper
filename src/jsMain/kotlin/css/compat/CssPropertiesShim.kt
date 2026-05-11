// Created by claude-sonnet-4-6
// Restores extension functions removed from kotlinx.css.properties in kotlin-wrappers pre.621.
// The pre.621 API replaced these functions with property setters using Border/BoxShadow data classes.
package kotlinx.css.properties

import kotlinx.css.*

fun StyledElement.border(
    width: LinearDimension,
    style: BorderStyle,
    color: Color,
    borderRadius: LinearDimension? = null,
) {
    this.border = Border(width, style, color)
    borderRadius?.let { this.borderRadius = it }
}

fun StyledElement.borderBottom(width: LinearDimension, style: BorderStyle, color: Color) {
    this.borderBottom = Border(width, style, color)
}

fun StyledElement.borderTop(width: LinearDimension, style: BorderStyle, color: Color) {
    this.borderTop = Border(width, style, color)
}

fun StyledElement.borderLeft(width: LinearDimension, style: BorderStyle, color: Color) {
    this.borderLeft = Border(width, style, color)
}

fun StyledElement.borderRight(width: LinearDimension, style: BorderStyle, color: Color) {
    this.borderRight = Border(width, style, color)
}

fun StyledElement.transition(
    property: String = "all",
    duration: Time = 0.s,
    timing: Timing = Timing.ease,
    delay: Time = 0.s,
) {
    this.transition += Transition(property, duration, timing, delay)
}

fun StyledElement.boxShadow(
    color: Color,
    offsetX: LinearDimension = 0.px,
    offsetY: LinearDimension = 0.px,
    blurRadius: LinearDimension = 0.px,
    spreadRadius: LinearDimension = 0.px,
) {
    boxShadow += BoxShadow(color = color, offsetX = offsetX, offsetY = offsetY, blurRadius = blurRadius, spreadRadius = spreadRadius)
}
