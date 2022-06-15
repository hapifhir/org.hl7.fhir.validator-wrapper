# validator-wrapper
#### This project contains the CLI, Desktop GUI, and Standalone Validation Server for the FHIR Validator

| CI Status (master) | Website Docker Image |
| :---: | :---: |
| [![Build Status][Badge-BuildPipelineMaster]][Link-BuildPipelineMaster] | [![Docker Status][Badge-DockerHub]][Link-DockerHub] |


## Building this Project

This project uses the [gradle build tool][Link-GradleWebpage] to build, and includes pre-build gradlew wrappers for common build tasks. 

### To build:

```
gradlew build
```

or 

```
gradlew.bat build
```

### To run a self-hosted version of the web UI:

```
gradlew run
```

or

```
gradlew.bat run
```

Detailed build instructions and documentation, including gradle setup can be found [here][Link-GithubIOBuilding].

## Additional Documentation
Supporting documentation and instructions can be found on our [github.io page][Link-GithubIO].

---
### Version Management:
We manage release versioning using the [semantic version plugin for gradle][Link-SemanticVersionPlugin]. 

### TODO / Known Issues
1. Localization

##### Have you found an issue not listed above? Do you have a feature request? Great! Submit it [here][Link-GitHubIssues] and we'll try to fix it as soon as possible.

### Maintenance
This project is maintained by [David Otasek][Link-davidGithub], [Grahame Grieve][Link-grahameGithub], and [Mark Iantorno][Link-markGithub] on behalf of the FHIR community.

[Link-AzureProject]: https://dev.azure.com/fhir-pipelines/validator-wrapper
[Link-BuildPipelineMaster]: https://dev.azure.com/fhir-pipelines/validator-wrapper/_build/latest?definitionId=38&branchName=master
[Link-DockerHub]: https://hub.docker.com/repository/docker/markiantorno/validator-wrapper/general
[Link-CoreGithubLatestRelease]: https://github.com/hapifhir/org.hl7.fhir.core/releases/latest
[Link-ValidatorWrapperGithubLatestRelease]: https://github.com/hapifhir/org.hl7.fhir.validator-wrapper/releases/latest
[Link-GitHubIssues]: https://github.com/hapifhir/org.hl7.fhir.validator-wrapper/issues
[Link-GradleWebpage]: https://gradle.org/
[Link-GradleKotlinDSLPrimer]: https://docs.gradle.org/current/userguide/kotlin_dsl.html
[Link-GradleInstall]: https://gradle.org/install/
[Link-GradleWrapper]: https://docs.gradle.org/current/userguide/gradle_wrapper.html
[Link-ValidatorConfluence]: https://confluence.hl7.org/display/FHIR/Using+the+FHIR+Validator
[Link-SemanticVersionPlugin]: https://github.com/ethauvin/semver-gradle

[Link-GithubIO]: https://hl7.github.io/docs/validator-wrapper
[Link-GithubIOBuilding]: https://hl7.github.io/docs/validator-wrapper/building

[Badge-BuildPipelineMaster]: https://dev.azure.com/fhir-pipelines/validator-wrapper/_apis/build/status/Master%20Branch%20Pipeline?branchName=master
[Badge-DockerHub]: https://img.shields.io/docker/v/markiantorno/validator-wrapper

[Link-davidGithub]: https://github.com/dotasek
[Link-grahameGithub]: https://github.com/grahamegrieve
[Link-markGithub]: https://github.com/markiantorno
