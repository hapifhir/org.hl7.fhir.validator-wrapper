# validator-wrapper


| CI Status (master) | Website Docker Image |
| :---: | :---: |
| [![Build Status][Badge-AzureMasterPipeline]][Link-AzureMasterPipeline] | [![Docker Status][Badge-DockerHub]][Link-DockerHub] |


This project contains the CLI, Desktop GUI, and Standalone Validation Server for the FHIR Validator

## Building this Project

This project uses the [gradle build tool][Link-GradleWebpage] to build, and includes pre-build gradlew wrappers for common build tasks. 

### To build:

On Mac or Linux:

```
gradlew build
```

On Windows:

```
gradlew.bat build
```

### To run a self-hosted version of the web UI:

On Mac or Linux:

```
gradlew run
```

On Windows:

```
gradlew.bat run
```

Detailed build instructions and documentation, including gradle setup can be found [here][Link-GithubIOBuilding].

## Additional Documentation
Supporting documentation and instructions can be found on our [github.io page][Link-GithubIO].

---
## Version Management:
We manage release versioning using the [semantic version plugin for gradle][Link-SemanticVersionPlugin]. 


## Releases

Releases and release notes are published to [GitHub][Link-GithubReleases], and a hosted version of the latest release can be found [here][Link-ValidatorWebsite].

## CI/CD

This project has pipelines hosted on [Azure Pipelines][Link-AzureProject]. 

* **Pull Request Pipeline** is automatically run for every Pull Request to ensure that the project can be built via gradle. [[Azure Pipeline]][Link-AzurePullRequestPipeline] [[source]](pull-request-pipeline.yml)
* **Master Branch Pipeline** is automatically run whenever code is merged to the master branch and builds the SNAPSHOT binaries. [[Azure Pipeline]][Link-AzureMasterPipeline][[source]](master-branch-pipeline.yml)
* **Release Branch Pipeline** is run manually whenever a release is ready to be made. It builds the [release binaries](#releases), uploads the docker image to [DockerHub][Link-DockerHub], updates the [validator web app][Link-ValidatorWebsite] and sends release notifications. [[Azure Pipeline]][Link-AzureReleasePipeline][[source]](release-branch-pipeline.yml)


## Maintenance

Have you found an issue? Do you have a feature request? Great! Submit it [here][Link-GithubIssues] and we'll try to fix it as soon as possible.

This project is maintained by [David Otasek][Link-davidGithub], [Grahame Grieve][Link-grahameGithub], and [Mark Iantorno][Link-markGithub] on behalf of the FHIR community.

[Link-DockerHub]: https://hub.docker.com/repository/docker/markiantorno/validator-wrapper/general


[Link-GithubCoreLatestRelease]: https://github.com/hapifhir/org.hl7.fhir.core/releases/latest
[Link-GithubReleases]: https://github.com/hapifhir/org.hl7.fhir.validator-wrapper/releases
[Link-GithubIssues]: https://github.com/hapifhir/org.hl7.fhir.validator-wrapper/issues
[Link-GradleWebpage]: https://gradle.org/
[Link-GradleKotlinDSLPrimer]: https://docs.gradle.org/current/userguide/kotlin_dsl.html
[Link-GradleInstall]: https://gradle.org/install/
[Link-GradleWrapper]: https://docs.gradle.org/current/userguide/gradle_wrapper.html
[Link-ValidatorConfluence]: https://confluence.hl7.org/display/FHIR/Using+the+FHIR+Validator
[Link-SemanticVersionPlugin]: https://github.com/ethauvin/semver-gradle

[Link-ValidatorWebsite]: https://validator.fhir.org/

[Link-GithubIO]: https://hl7.github.io/docs/validator-wrapper
[Link-GithubIOBuilding]: https://hl7.github.io/docs/validator-wrapper/building

[Link-AzureProject]: https://dev.azure.com/fhir-pipelines/validator-wrapper
[Badge-AzureMasterPipeline]: https://dev.azure.com/fhir-pipelines/validator-wrapper/_apis/build/status/Master%20Branch%20Pipeline?branchName=master
[Link-AzureMasterPipeline]: https://dev.azure.com/fhir-pipelines/validator-wrapper/_build?definitionId=38
[Link-AzurePullRequestPipeline]: https://dev.azure.com/fhir-pipelines/validator-wrapper/_build?definitionId=39
[Link-AzureReleasePipeline]: https://dev.azure.com/fhir-pipelines/validator-wrapper/_build?definitionId=40

[Badge-DockerHub]: https://img.shields.io/docker/v/markiantorno/validator-wrapper

[Link-davidGithub]: https://github.com/dotasek
[Link-grahameGithub]: https://github.com/grahamegrieve
[Link-markGithub]: https://github.com/markiantorno
