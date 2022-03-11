package ui.components.options

import css.const.HL7_RED
import css.const.WHITE
import css.const.SWITCH_GRAY
import css.text.TextStyle

import kotlinx.css.*
import model.PackageInfo

import react.*
import styled.StyleSheet
import styled.css
import styled.styledDiv
import styled.styledSpan
import ui.components.buttons.imageButton
import ui.components.options.menu.dropDownMultiChoice

import api.sendIGVersionsRequest

import kotlinx.coroutines.launch

import mainScope

external interface IgSelectorProps : RProps {
    var fhirVersion: String
    var onUpdateIg: (PackageInfo, Boolean) -> Unit
    var igList: MutableList<PackageInfo>
    var igPackageNameList :MutableList<Pair<String, Boolean>>
    var onUpdatePackageName: (String, Boolean) -> Unit
    var selectedIgSet : MutableSet<PackageInfo>
}

class IgSelectorState : RState {
    var packageVersions = mutableListOf<Pair<PackageInfo, Boolean>>()
}

class IgSelector : RComponent<IgSelectorProps, IgSelectorState>() {

    init {
        state = IgSelectorState()
    }

    private fun setIGVersions(igPackageName : String) {
        mainScope.launch {
            val simplifierPackages : MutableList<PackageInfo> =
            try {
                val igResponse = sendIGVersionsRequest(igPackageName)
                igResponse.packageInfo
            } catch (e : Exception) {
               mutableListOf()
            }

            val registryPackages : MutableList<PackageInfo> = props.igList.filter{ it.id == igPackageName }.toMutableList();
            val allPackages = (registryPackages + simplifierPackages).distinctBy{it.version}
                .sortedBy{it.version}.reversed().toMutableList()

            setState {
                packageVersions = allPackages.map { Pair(it, false) }.toMutableList()
            }
        }
    }

    override fun RBuilder.render() {
        styledDiv {
            css {
                +IgSelectorStyle.mainDiv
            }
            styledSpan {
                css {
                    +TextStyle.optionsDetailText
                    +IgSelectorStyle.title
                }
                +"You can validate against one or more published implementation guides. Select IGs using the dropdown menus below and click the "
                styledSpan {
                    css {
                        fontStyle = FontStyle.italic
                    }
                    + "Add"
                }
                +" button to include them in your validation."
            }
            styledSpan {
                dropDownMultiChoice {
                    choices = props.igPackageNameList
                    buttonLabel = "Select IG"
                    onSelected = { igPackageName ->
                        props.onUpdatePackageName(igPackageName, true)
                        setIGVersions(igPackageName)
                    }
                    multichoice = false
                    searchEnabled = true
                    searchHint = "Search IGs..."
                }
                val versions = state.packageVersions.filter { it.first.fhirVersionMatches(props.fhirVersion)}
                    .map{Pair(it.first.version ?: "", it.second)}
                    .toMutableList()
                val versionSelected = versions.filter { it.second }.isNotEmpty()
                styledSpan {
                    css {
                        margin(left = 8.px)
                    }
                    dropDownMultiChoice {
                        choices = versions
                        buttonLabel = if (versions.size > 0) "Select IG version" else "No compatible versions"
                        onSelected = { igVersion ->
                            setState {
                                packageVersions = state.packageVersions.map{Pair(it.first, it.first.version == igVersion)}.toMutableList()
                            }
                        }
                        multichoice = false
                        searchEnabled = false
                    }
                }
                styledSpan {
                    css {
                        margin(left = 8.px)
                    }
                    imageButton {
                        backgroundColor = WHITE
                        borderColor = if (versionSelected) {HL7_RED} else { SWITCH_GRAY }
                        image = "images/add_circle_black_24dp.svg"
                        label = "Add"
                        onSelected = {
                            if (versionSelected)
                            props.onUpdateIg(state.packageVersions.first{it.second}.first, true)
                        }
                    }
                }
            }
            styledDiv {
                css {
                    padding(top = 24.px)
                    + if (props.selectedIgSet.isEmpty()) TextStyle.optionsDetailText else TextStyle.optionName
                }
                + ("Selected IGs (${props.selectedIgSet.size})" + if (props.selectedIgSet.isEmpty()) { "" } else { ":"})
            }
            styledDiv {
                css {
                    +IgSelectorStyle.selectedIgsDiv
                    if (!props.selectedIgSet.isEmpty()) {
                        padding(top = 16.px)
                    }
                }
                props.selectedIgSet.forEach { _packageInfo ->
                    igDisplay {
                        fhirVersion = props.fhirVersion
                        packageInfo = _packageInfo

                        onDelete = {
                            props.onUpdateIg(_packageInfo, false)
                        }
                    }
                }
            }
        }
    }

}


/**
 * React Component Builder
 */
fun RBuilder.igSelector(handler: IgSelectorProps.() -> Unit): ReactElement {
    return child(IgSelector::class) {
        this.attrs(handler)
    }
}

/**
 * CSS
 */
object IgSelectorStyle : StyleSheet("IgSelectorStyle", isStatic = true) {
    val mainDiv by css {
        display = Display.flex
        flexDirection = FlexDirection.column
        padding(horizontal = 8.px)
    }
    val title by css {
        paddingBottom = 16.px
    }
    val selectedIgsDiv by css {
        display = Display.flex
        flexDirection = FlexDirection.row
        flexWrap = FlexWrap.wrap
    }
}
