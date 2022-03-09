package ui.components.options

import css.const.HL7_RED
import css.const.WHITE
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

    private fun getIGVersionsFromIgList(igPackageName: String) {

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
                    buttonLabel = "Select IGs"
                    onSelected = { igPackageName ->
                        props.onUpdatePackageName(igPackageName, true)
                        setIGVersions(igPackageName)
                    }
                    multichoice = false
                    searchEnabled = true
                    searchHint = "Search IGs..."
                }

                styledSpan {
                    css {
                        margin(left = 8.px)
                    }
                    dropDownMultiChoice {
                        choices = state.packageVersions.filter { it.first.fhirVersionMatches(props.fhirVersion)}
                            .map{Pair(it.first.version ?: "", it.second)}
                            .toMutableList()
                        buttonLabel = "Select version"
                        onSelected = { igVersion ->
                            setState {
                                packageVersions = state.packageVersions.map{Pair(it.first, it.first.version == igVersion)}.toMutableList()
                            }
                        }
                        multichoice = false
                        searchEnabled = false
                        searchHint = "Select version"
                    }
                }
                styledSpan {
                    css {
                        margin(left = 8.px)
                    }
                    imageButton {
                        backgroundColor = WHITE
                        borderColor = HL7_RED
                        image = "images/add_circle_black_24dp.svg"
                        label = "Add"
                        onSelected = {
                            props.onUpdateIg(state.packageVersions.first{it.second}.first, true)
                        }
                    }
                }
            }
            styledDiv {
                css {
                    +IgSelectorStyle.selectedIgsDiv
                    if (props.igPackageNameList.any { it.second }) {
                        padding(top = 8.px)
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
