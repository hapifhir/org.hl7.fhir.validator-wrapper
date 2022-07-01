# HL7 Validator 

# Validator Routes

### Validate a Resource
- **Route:** 
`POST /validate` 
- **Body:** 
the JSON resource to validate 
- **Response:** 
a JSON object [ValidationResponse](https://github.com/hapifhir/org.hl7.fhir.validator-wrapper/blob/master/src/commonMain/kotlin/model/ValidationResponse.kt)



Sample Request Payload:
```json
{
    "cliContext": {
        "targetVer": "4.0.1",
        "sv": "4.0.1",
        "igs": [
            "hl7.fhir.us.core#4.0.0"
        ]
    },
    "filesToValidate": [
        {
            "fileName": "manually_entered_file.json",
            "fileContent": "{\"resourceType\": \"Patient\"}",
            "fileType": "json"
        }
    ],
    "sessionId": "32fc25cf-020e-4492-ace5-03fe904d22e0"
}
```



Sample Response:
```json
{
  "outcomes" : [ {
    "fileInfo" : {
      "fileName" : "manually_entered_file.json",
      "fileContent" : "{\n  \"resourceType\": \"Patient\"}",
      "fileType" : "json"
    },
    "issues" : [ ]
  } ],
  "sessionId" : "32fc25cf-020e-4492-ace5-03fe904d22e0"
}
```

### List Validator Version
- **Route:** 
`GET /validator/version` 
- **Response:** 
String



Sample Response:
```
5.6.39
```

### List IGs
- **Route:** 
`GET /ig` 
- **Response:** 
a JSON ARRAY [IGResponse](https://github.com/hapifhir/org.hl7.fhir.validator-wrapper/blob/master/src/commonMain/kotlin/model/IGResponse.kt)


Sample Response:
```json
{
  "packageInfo" : [ {
    "id" : "hl7.fhir.us.core",
    "version" : "5.0.1",
    "fhirVersion" : "4.0.1",
    "url" : "http://hl7.org/fhir/us/core/STU5.0.1"
  }, {
    "id" : "hl7.fhir.au.base",
    "version" : "1.0.1",
    "fhirVersion" : "3.0.1",
    "url" : "http://hl7.org.au/fhir/base/2019Feb"
  },
  ...
    {
    "id" : "hl7.fhir.us.davinci-pcde",
    "version" : null,
    "fhirVersion" : null,
    "url" : null
  } ]
}
```

### List IG versions
- **Route:** 
`GET /igVersions/{igPackageName}` 
- **Path Params:** 
`igPackageName`
- **Response:** 
a JSON ARRAY [IGResponse](https://github.com/hapifhir/org.hl7.fhir.validator-wrapper/blob/master/src/commonMain/kotlin/model/IGResponse.kt)



Sample Response:
```json
{
  "packageInfo" : [ {
    "id" : "hl7.fhir.us.core",
    "version" : "0.0.0",
    "fhirVersion" : "STU3",
    "url" : "https://packages.simplifier.net/hl7.fhir.us.core/0.0.0"
  }, {
    "id" : "hl7.fhir.us.core",
    "version" : "1.0.0",
    "fhirVersion" : "STU3",
    "url" : "https://packages.simplifier.net/hl7.fhir.us.core/1.0.0"
  }, 
  ...
   {
    "id" : "hl7.fhir.us.core",
    "version" : "5.0.1",
    "fhirVersion" : "R4",
    "url" : "https://packages.simplifier.net/hl7.fhir.us.core/5.0.1"
  } ]
}
```

### List Supported FHIR versions
- **Route:** 
`GET /versions` 
- **Response:**
a JSON ARRAY [FhirVersionsResponse](https://github.com/hapifhir/org.hl7.fhir.validator-wrapper/blob/master/src/commonMain/kotlin/model/FhirVersionsResponse.kt)


Sample Response:
```
{
  "versions" : [ "1.0.2", "1.4.0", "3.0.2", "4.0.1", "4.1.0", "4.3.0", "5.0", "4.6.0" ]
}
```


# Package & Terminology Routes

### Validate Terminology Server
- **Route:** 
`POST /terminology` 
- **Response:** 
a JSON indicating server validity and details [TerminologyServerResponse](https://github.com/hapifhir/org.hl7.fhir.validator-wrapper/blob/master/src/commonMain/kotlin/model/TerminologyServer.kt) 

### Terminology Server Status
- **Route:** 
`GET /TxStatus` 
- **Response:** 
Boolean indicating whether the [server](http://tx.fhir.org) is up
		
### Package Server Status
- **Route:** 
`GET /packStatus` 
- **Response:** 
Boolean indicating whether the [server](http://packages2.fhir.org) is up
		



