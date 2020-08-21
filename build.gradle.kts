import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack

group = "org.hl7.fhir.validator"
version = "1.0-SNAPSHOT"

buildscript {
    repositories {
        google()
        jcenter()
        maven(url = "https://dl.bintray.com/kotlin/kotlin-eap")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:3.6.3")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.72")
    }
}

plugins {
    application //to run JVM part
    kotlin("multiplatform") version "1.3.72"
    kotlin("plugin.serialization") version "1.3.72"
    id ("org.openjfx.javafxplugin") version "0.0.8"
}

repositories {
    google()
    jcenter()
    mavenLocal()
    mavenCentral()
    maven("https://dl.bintray.com/kotlin/ktor")
    maven("https://dl.bintray.com/kotlin/kotlinx")
    maven("https://dl.bintray.com/kotlin/kotlin-eap")
    maven("https://dl.bintray.com/kotlin/kotlin-js-wrappers")
    maven("https://oss.sonatype.org/content/groups/public/")
}

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
//        binaries.executable() // Keep this as we will need it for v1.4
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("io.ktor:ktor-serialization:${property("ktorVersion")}")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:${property("serializationVersion")}")
                implementation("com.fasterxml.jackson.core:jackson-databind:${property("jacksonVersion")}")
                implementation("ca.uhn.hapi.fhir:org.hl7.fhir.validation:${property("fhirCoreVersion")}")
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
                //implementation(kotlin("stdlib", "${property("kotlinVersion")}")) // or "stdlib-jdk8"
                implementation(kotlin("stdlib-jdk8"))
                implementation("io.ktor:ktor-server-jetty:${property("ktorVersion")}")
                implementation("io.ktor:ktor-server-core:${property("ktorVersion")}")
                implementation("io.ktor:ktor-websockets:${property("ktorVersion")}")
                implementation("io.ktor:ktor-jackson:${property("ktorVersion")}")

                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:${property("serializationVersion")}") // JVM dependency

                implementation("ch.qos.logback:logback-classic:1.2.3")
                implementation("org.litote.kmongo:kmongo-coroutine-serialization:3.12.2")
                implementation("no.tornado:tornadofx:${property("tornadoFXVersion")}")
                implementation("ca.uhn.hapi.fhir:org.hl7.fhir.validation:${property("fhirCoreVersion")}")
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation("io.ktor:ktor-server-tests:${property("ktorVersion")}")
            }
        }

        val jsMain by getting {
            dependencies {

                implementation(kotlin("stdlib-js"))

                implementation("io.ktor:ktor-client-js:${property("ktorVersion")}") //include http&websockets
                implementation("io.ktor:ktor-client-json-js:${property("ktorVersion")}")
                implementation("io.ktor:ktor-client-serialization-js:${property("ktorVersion")}")
                implementation("io.ktor:ktor-client-jackson:${property("ktorVersion")}")

                implementation("org.jetbrains:kotlin-react:${property("kotlinReactVersion")}")
                implementation("org.jetbrains:kotlin-react-dom:${property("kotlinReactVersion")}")
                implementation("org.jetbrains:kotlin-react-router-dom:${property("kotlinReactRouterVersion")}")
                implementation("org.jetbrains:kotlin-styled:${property("kotlinStyledVersion")}")
                implementation("org.jetbrains.kotlinx:kotlinx-html-js:0.7.1")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${property("kotlinxCoroutinesVersion")}")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-js:${property("serializationVersion")}")

                implementation(npm("react", "${property("reactVersion")}"))
                implementation(npm("react-dom", "${property("reactVersion")}"))
                implementation(npm("react-router-dom", "5.1.2"))
                implementation(npm("styled-components", "4.4.1")) // Animations don't work with styled components 5+
                implementation(npm("inline-style-prefixer", "5.1.0"))
                implementation(npm("text-encoding"))
                implementation(npm("abort-controller"))
                implementation(npm("fs"))
                implementation(npm("bufferutil")) //Dont' uncomment this
                implementation(npm("utf-8-validate"))
            }
        }

        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
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
