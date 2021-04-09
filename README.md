# validator-wrapper
#### This project contains the CLI, Desktop GUI, and Standalone Validation Server for the FHIR Validator

| CI Status (master) | Website Docker Image |
| :---: | :---: |
| [![Build Status][Badge-BuildPipelineMaster]][Link-BuildPipelineMaster] | [![Docker Status][Badge-DockerHub]][Link-DockerHub] |

### Warning
This project is still not "officially" released, and may contain errors/bugs/dragons/smooth-jazz. During this initial 
period, your patience is **greatly appreciated**.

The validator CLI will still be generated and hosted as normal within the [hapifhir/core.hl7.fhir.core][Link-CoreGithubLatestRelease] 
_for now_. Be advised that on release of v1.0 of the this project, we will stop publishing the cli as part of the core, 
and users will be expected to download the cli jar from the [latest release][Link-ValidatorWrapperGithubLatestRelease] 
of this project.

### CI/CD
All integration and delivery done on Azure pipelines. Azure project can be viewed [here][Link-AzureProject].

### Docker 
Docker image for the fullstack website is stored [on DockerHub][Link-DockerHub], and can be downloaded and run locally.

Updates to the docker image are triggered through the Azure Pipelines CI/CD.

Building Locally
---
This project uses the [gradle build tool][Link-GradleWebpage] to build. In particular, we take advantage of 
[Gradle's Kotlin DSL][Link-GradleKotlinDSLPrimer] as an alternative syntax to the traditional Groovy DSL.

To generate the jar containing all resources locally:
1. Ensure you have Gradle [installed on your system][Link-GradleInstall]
2. Generate the [Gradle Wrapper files][Link-GradleWrapper] locally by running the command `gradle wrapper --gradle-version=6.7`
3. Build the project by running the command `./gradlew build`
4. This will generate three jar files in the `/build/libs/` directory. The only one we care about is 
`validator-wrapper-jvm-{$project-version}.jar`

Running the jar
---
There are three possible ways this jar can be utilized:
#### As a full-stack hosted server:
Execute the jar by providing the argument `'-startServer'`. This boots the Ktor validation back end and KotlinJS 
front-end. Refer to the `jvmMain/resources/application.conf` file in the resources directory to view the different deployment flavours available. 
These deployment types can be set through the environment variable `ENVIRONMENT`. If no such environment variable is 
set, the application will default to a `dev` type deployment.

#### As a locally run, short-lived, 'desktop' application:
Execute the jar by providing the argument `'-gui'`. This boots the Ktor server locally on the port 8080, and starts a 
wrapped instance of the KotlinJS front end within a Chromium web window to appear as a desktop application. This 
wrapped website should mimic all the same functionality of the full KotlinJS website in the full-stack hosted server. 
Once the Chromium browser window is closed, the local Ktor server is also shutdown.

#### As the traditional validator cli:
We realize that for many users, the cli is still the primary way in which validation is performed, so we've made
it possible to still execute this jar, as done previously, from the command line. All validator cli functionality 
remains as detailed [on the confluence wiki][Link-ValidatorConfluence].

**N.B.**
If you attempt to run this as both a full-stack server and a locally hosted application (by including both the 
`startServer` and `-gui` commands from the cli, the full-stack server takes priority, and the desktop version will 
not be booted.

---
#### Version Management:
We manage release versioning using the [semantic version plugin for gradle][Link-SemanticVersionPlugin]. 

### TODO / Known Issues
1. Localization is not enabled...yet

##### Have you found an issue not listed above? Do you have a feature request? Great! Submit it [here][Link-GitHubIssues] and we'll try to fix it as soon as possible.

### Maintenance
This project is maintained by [Grahame Grieve][Link-grahameGithub] and [Mark Iantorno][Link-markGithub] on behalf of the FHIR community.

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

[Badge-BuildPipelineMaster]: https://dev.azure.com/fhir-pipelines/validator-wrapper/_apis/build/status/Master%20Branch%20Pipeline?branchName=master
[Badge-DockerHub]: https://img.shields.io/docker/v/markiantorno/validator-wrapper

[Link-grahameGithub]: https://github.com/grahamegrieve
[Link-markGithub]: https://github.com/markiantorno
