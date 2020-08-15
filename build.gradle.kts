import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack

val kotlinVersion = "1.3.72"
val serializationVersion = "0.20.0"
val ktorVersion = "1.3.2"
val fhirCoreVersion = "5.0.17-SNAPSHOT"
val jacksonVersion = "2.11.1"
val tornadoFXVersion = "1.7.20"

plugins {
    application //to run JVM part
    kotlin("multiplatform") version "1.3.72"
    kotlin("plugin.serialization") version "1.3.72"
    id ("org.openjfx.javafxplugin") version "0.0.8"
}

repositories {
    mavenLocal()
    jcenter()
    mavenCentral()

    maven { url = uri("https://kotlin.bintray.com/ktor") }
    maven { url = uri("https://plugins.gradle.org/m2/") }
    maven { url = uri("https://dl.bintray.com/kotlin/kotlin-eap") }

    maven { url = uri("https://oss.sonatype.org/content/groups/public/") }
    maven("https://kotlin.bintray.com/kotlin-js-wrappers/") // react, styled, ...
    //maven("https://dl.bintray.com/kotlin/kotlin-js-wrappers/")
}

group = "org.hl7.fhir.validator"
version = "1.0-SNAPSHOT"

kotlin {
    /* Targets configuration omitted. 
    *  To find out how to configure the targets, please follow the link:
    *  https://kotlinlang.org/docs/reference/building-mpp-with-gradle.html#setting-up-targets */
    jvm {
        withJava()
        val main by compilations.getting {
            kotlinOptions {
                // Setup the Kotlin compiler options for the 'main' compilation:
                jvmTarget = "1.8"
            }
        }
    }

    js {
        browser {
            dceTask {
                keep("ktor-ktor-io.\$\$importsForInline\$\$.ktor-ktor-io.io.ktor.utils.io")
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("io.ktor:ktor-serialization:$ktorVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:$serializationVersion")

                implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
                implementation("ca.uhn.hapi.fhir:org.hl7.fhir.validation:$fhirCoreVersion")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation("io.ktor:ktor-server-jetty:$ktorVersion")
                implementation("io.ktor:ktor-server-core:$ktorVersion")
                implementation("ch.qos.logback:logback-classic:1.2.3")
                implementation(kotlin("stdlib", kotlinVersion)) // or "stdlib-jdk8"
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:$serializationVersion") // JVM dependency
                implementation("io.ktor:ktor-websockets:$ktorVersion")
                implementation("io.ktor:ktor-jackson:$ktorVersion")
                implementation("ch.qos.logback:logback-classic:1.2.3")
                implementation("org.litote.kmongo:kmongo-coroutine-serialization:3.12.2")

                implementation("no.tornado:tornadofx:$tornadoFXVersion")

                implementation("ca.uhn.hapi.fhir:org.hl7.fhir.validation:$fhirCoreVersion")
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation("io.ktor:ktor-server-tests:$ktorVersion")
            }
        }

        val jsMain by getting {
            dependencies {
                implementation(kotlin("stdlib-js"))

                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-js:$serializationVersion")

                implementation(npm("text-encoding"))
                implementation(npm("abort-controller"))

                implementation("io.ktor:ktor-client-js:$ktorVersion") //include http&websockets

                implementation(npm("bufferutil")) //Dont' uncomment this
                implementation(npm("utf-8-validate"))

                //ktor client js json
                implementation("io.ktor:ktor-client-json-js:$ktorVersion")
                implementation("io.ktor:ktor-client-serialization-js:$ktorVersion")
                implementation("io.ktor:ktor-client-jackson:$ktorVersion")
                implementation(npm("fs"))

                implementation("org.jetbrains:kotlin-react:16.13.0-pre.94-kotlin-1.3.70")
                implementation("org.jetbrains:kotlin-react-dom:16.13.0-pre.94-kotlin-1.3.70")
                implementation(npm("react", "16.13.1"))
                implementation(npm("react-dom", "16.13.1"))

                implementation("org.jetbrains:kotlin-styled:1.0.0-pre.94-kotlin-1.3.70")
                implementation(npm("styled-components", "4.4.1")) // Animations don't work with styled components 5+
                implementation(npm("inline-style-prefixer"))

                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.8")
            }
        }
    }
}

javafx {
    version = "11.0.2"
    modules("javafx.controls", "javafx.graphics", "javafx.web")
}

application {
    mainClassName = "ServerKt"
}

// include JS artifacts in any JAR we generate
tasks.getByName<Jar>("jvmJar") {
    val taskName = if (project.hasProperty("isProduction")) {
        "jsBrowserProductionWebpack"
    } else {
        "jsBrowserDevelopmentWebpack"
    }
    val webpackTask = tasks.getByName<KotlinWebpack>(taskName)
    dependsOn(webpackTask) // make sure JS gets compiled first
    from(File(webpackTask.destinationDirectory, webpackTask.outputFileName)) // bring output file along into the JAR
}

distributions {
    main {
        contents {
            from("$buildDir/libs") {
                rename("${rootProject.name}-jvm", rootProject.name)
                into("lib")
            }
        }
    }
}

// Alias "installDist" as "stage" for Heroku
tasks.create("stage") {
    dependsOn(tasks.getByName("installDist"))
}

tasks.getByName<JavaExec>("run") {
    classpath(tasks.getByName<Jar>("jvmJar")) // so that the JS artifacts generated by `jvmJar` can be found and served
}


tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "ServerKt"
    }

    // To add all of the dependencies otherwise a "NoClassDefFoundError" error
    from(sourceSets.main.get().output)

    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}
