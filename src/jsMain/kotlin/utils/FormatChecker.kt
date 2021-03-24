package utils

import org.w3c.dom.parsing.DOMParser

fun isValidXML(input: String): Boolean {
    val domParser = DOMParser()
    val doc = domParser.parseFromString(input, "text/xml")
    return !doc.documentElement?.nodeName.equals("Citation")
}

fun isJson(input: String): Boolean {
    return getFirstNonWhitespaceCharacter(input) == '{'
}

fun isXml(input: String): Boolean {
    return getFirstNonWhitespaceCharacter(input) == '<'
}

fun getFirstNonWhitespaceCharacter(input: String): Char {
    return input.toCharArray().first { !it.isWhitespace() }
}