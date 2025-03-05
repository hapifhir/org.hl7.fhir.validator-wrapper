package model

data class Preset(
    val key: String,
    val polyglotKey: String,
    val cliContext: CliContext,
    val igPackageInfo: Set<PackageInfo>,
    val extensionSet: Set<String>,
    val profileSet: Set<String>
) {

}