These examples are intended to test validation using several pre-defined validation engine bases (IGs, relevant 
settings, etc). The test cases are only intended to ensure that the correct settings are being used. The test cases are
not intended to test validation itself, as this is done through fhir-test-cases.

The contents of these test files are described below:


# ccda.xml

This is a combination of examples. The base of the document is this complete example:

https://github.com/HL7/CDA-ccda/blob/master/input/examples/care-plan-complete-example.xml

To minimize the expected errors, the problem list from the following was added to the structured body element:

https://github.com/HL7/C-CDA-Examples/blob/master/Documents/CCD/CCD%202/CCD.xml

Validating through CDA and CCDA will give slightly different results. 

# ips.json

This was taken directly from https://build.fhir.org/ig/HL7/fhir-ips/Bundle-bundle-minimal.json.html

# iss-nz.json

This was taken from the confluence page for the NZ IPS: https://confluence.hl7.org/display/HNZ/8.+NZ+Patient+Summary+%28NZPS%29+profiling+test+track?focusedCommentId=208470365

Specifically downloaded from this link: https://terminz.azurewebsites.net/fhir/Patient/$summary?profile=http://hl7.org/fhir/uv/ips/StructureDefinition/Bundle-uv-ips&identifier=https://standards.digital.health.nz/ns/nhi-id|ZKT9319&_format=json

# sql-on-fhir

This was taken directly from the simple example on https://build.fhir.org/ig/FHIR/sql-on-fhir-v2/ but with the additional
structure and fields required to make it a valid resource.


