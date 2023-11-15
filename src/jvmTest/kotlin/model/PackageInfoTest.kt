package model

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class PackageInfoTest {

    @ParameterizedTest
    @MethodSource("listsToCompare")
    fun versionComparatorWorks(unsorted: List<PackageInfo>, sorted : List<PackageInfo>) {
        assertEquals(sorted, unsorted.sortedWith(PackageInfo.VersionComparator()))
    }


    @Test
    fun fhirVersionMatches() {
        assertTrue(PackageInfo(fhirVersion = null).fhirVersionMatches("4.0.1"))
        assertTrue(PackageInfo(fhirVersion = "4.0.1").fhirVersionMatches("4.0.1"))
        assertTrue(PackageInfo(fhirVersion = "4.0.1").fhirVersionMatches("4.0.5"))
        assertTrue(PackageInfo(fhirVersion = "4.0.1").fhirVersionMatches("4.0"))
        assertFalse(PackageInfo(fhirVersion = "4.0.1").fhirVersionMatches("4.1.1"))
        assertFalse(PackageInfo(fhirVersion = "4.1.1").fhirVersionMatches("4.0.1"))
    }

    @Test
    fun extractMajorMinor() {
        assertEquals("4.0", PackageInfo().extractMajorMinor("4.0.1"))
        assertEquals("4.0", PackageInfo().extractMajorMinor("4.0.1.1"))
        assertEquals("4", PackageInfo().extractMajorMinor("4"))
    }

    companion object {
        val current_package = PackageInfo(version = "current")
        val _0_0_1_package = PackageInfo(version = "0.0.1")
        val _0_0_2_package = PackageInfo(version = "0.0.2")
        val _0_1_2_package = PackageInfo(version = "0.1.2")
        val _0_1_2_snapshot_package = PackageInfo(version = "0.1.2-snapshot")
        val _1_5_0_package = PackageInfo(version = "1.5.0")
        val _6_0_0_package = PackageInfo(version = "6.0.0")
        val _15_5_0_package = PackageInfo(version = "15.5.0")
        @JvmStatic
        fun listsToCompare() = listOf(
            Arguments.of(listOf(_0_0_1_package), listOf(_0_0_1_package)),
            Arguments.of(listOf(_0_0_2_package, _0_0_1_package), listOf(_0_0_1_package, _0_0_2_package)),

            Arguments.of(listOf(_15_5_0_package, _0_0_2_package, _1_5_0_package), listOf(_0_0_2_package, _1_5_0_package, _15_5_0_package)),
            Arguments.of(
                listOf(current_package, _6_0_0_package, _0_1_2_snapshot_package, _0_1_2_package, _15_5_0_package, _0_0_2_package, _1_5_0_package),
                listOf(current_package, _0_0_2_package, _0_1_2_package, _0_1_2_snapshot_package, _1_5_0_package, _6_0_0_package, _15_5_0_package))
            )
    }
}