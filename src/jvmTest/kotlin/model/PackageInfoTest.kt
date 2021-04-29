package model

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class PackageInfoTest {

    @Test
    fun fhirVersionMatches() {
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
}