import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack

plugins {
    // Make sure the version for multiplatform and plugin.serialization match kotlinVersion in gradle.properties
    kotlin("multiplatform") version "1.9.10"
    kotlin("plugin.serialization") version "1.9.10"

    id("org.hidetake.ssh") version "2.10.1"
    id("org.openjfx.javafxplugin") version "0.0.8"
    id("net.thauvin.erik.gradle.semver") version "1.0.4"
    application
}
group = "org.hl7.fhir"

repositories {
    google()
    mavenLocal()
    mavenCentral()
    maven {
        url = uri("https://jitpack.io")
    }
    maven {
        url = uri("https://central.sonatype.com/repository/maven-snapshots/")
    }
    gradlePluginPortal()
}

// fhirCoreVersion transitively resolves jackson-databind:2.21.x, whose Gradle module metadata
// (absent in mavenLocal but present on Maven Central) aligns all Jackson modules to 2.21.x via
// the Jackson BOM. This causes jackson-module-kotlin:2.21.x to appear on the compile classpath;
// that version was compiled with Kotlin 2.1, whose metadata is unreadable by our Kotlin 1.9.x
// compiler. This is invisible locally because the mavenLocal copy of jackson-databind:2.21.x
// lacks the .module file that triggers the alignment. On a clean CI cache it always reproduces.
// Pin jackson-module-kotlin to a Kotlin-1.x-compatible version until kotlinVersion reaches 2.x.
//
// Similarly, org.hl7.fhir.utilities (fhirCoreVersion) transitively pulls in okhttp-jvm, whose
// newest versions depend on kotlin-stdlib:2.2.x - also unreadable by our Kotlin 1.9.x compiler.
// Pin kotlin-stdlib to our own kotlinVersion until we upgrade past Kotlin 2.0.
configurations.all {
    resolutionStrategy {
        force("com.fasterxml.jackson.module:jackson-module-kotlin:${property("jacksonVersion")}")
        force("org.jetbrains.kotlin:kotlin-stdlib:${property("kotlinVersion")}")
    }
}

tasks.register<Copy>("copySemver") {
    from("version.properties")
    into("src/jvmMain/resources/")
}

// org.hl7.fhir.utilities (fhirCoreVersion) transitively depends on okhttp-jvm/okio-jvm, both of
// which are compiled with Kotlin 2.2 metadata that our Kotlin 1.9.x compiler cannot read even
// after pinning kotlin-stdlib above. Skip the metadata version check until kotlinVersion reaches 2.x.
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.freeCompilerArgs += "-Xskip-metadata-version-check"
}
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompileCommon>().configureEach {
    kotlinOptions.freeCompilerArgs += "-Xskip-metadata-version-check"
}

kotlin {
    jvm {
        compilations {
            all {
                kotlinOptions.jvmTarget = "17"
            }
        }
        withJava()

        tasks.test { useJUnit() }
        testRuns["test"].executionTask.configure {
            useJUnit()
        }
    }
    js(IR) {
        useCommonJs()
        binaries.executable()
        browser {
            binaries.executable()
            webpackTask {
                cssSupport {
                    enabled.set(true)
                }
            }
            runTask {
                cssSupport {
                    enabled.set(true)
                }
            }
            testTask {
                useKarma {
                    useChromeHeadless()
                    webpackConfig.cssSupport {
                        enabled.set(true)
                    }
                }
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("org.jetbrains.kotlin:kotlin-reflect:${property("kotlinVersion")}")
                implementation("com.fasterxml.jackson.core:jackson-databind:${property("jacksonVersion")}")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${property("kotlinxSerializationVersion")}")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:${property("kotlinxSerializationVersion")}")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-protobuf:${property("kotlinxSerializationVersion")}")
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
                implementation("ca.uhn.hapi.fhir:org.hl7.fhir.validation:${property("fhirCoreVersion")}")
                implementation("ca.uhn.hapi.fhir:org.hl7.fhir.utilities:${property("fhirCoreVersion")}")
                // https://mvnrepository.com/artifact/org.fhir/ucum
                implementation("org.fhir:ucum:1.0.9")

                implementation("io.ktor:ktor-server-netty:${property("ktorVersion")}")
                implementation("io.ktor:ktor-server-html-builder:${property("ktorVersion")}")
                implementation("io.ktor:ktor-server-jetty:${property("ktorVersion")}")
                implementation("io.ktor:ktor-server-core:${property("ktorVersion")}")
                implementation("io.ktor:ktor-server-cors:${property("ktorVersion")}")
                implementation("io.ktor:ktor-server-compression:${property("ktorVersion")}")
                implementation("io.ktor:ktor-server-content-negotiation:${property("ktorVersion")}")

                implementation("io.ktor:ktor-server-call-logging:${property("ktorVersion")}")

                implementation("io.ktor:ktor-websockets:${property("ktorVersion")}")
                implementation("io.ktor:ktor-server-content-negotiation:${property("ktorVersion")}")
                implementation("io.ktor:ktor-events:${property("ktorVersion")}")

                implementation("io.ktor:ktor-serialization-kotlinx-json:${property("ktorVersion")}")

                implementation("io.ktor:ktor-serialization-gson:${property("ktorVersion")}")
                implementation("io.ktor:ktor-serialization-jackson:${property("ktorVersion")}")
                implementation("io.ktor:ktor-serialization:${property("ktorVersion")}")
                implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:${property("kotlinxHtmlVersion")}")
                implementation("io.insert-koin:koin-ktor:${property("koinVersion")}")
                //TODO
                implementation("io.ktor:ktor-client-core:${property("ktorVersion")}")
                implementation("io.ktor:ktor-client-json:${property("ktorVersion")}")
                implementation("io.ktor:ktor-client-cio:${property("ktorVersion")}")
                implementation("io.ktor:ktor-client-logging:${property("ktorVersion")}")
                implementation("io.ktor:ktor-client-serialization:${property("ktorVersion")}")
                implementation("io.ktor:ktor-client-jackson:${property("ktorVersion")}")
                implementation("io.ktor:ktor-client-gson:${property("ktorVersion")}")
                implementation("io.ktor:ktor-client-serialization-jvm:${property("ktorVersion")}")
                implementation("io.ktor:ktor-client-content-negotiation:${property("ktorVersion")}")
                implementation("com.squareup.okhttp3:okhttp:4.9.0")

                implementation("ch.qos.logback:logback-classic:${property("logbackVersion")}")
                implementation("org.litote.kmongo:kmongo-coroutine-serialization:${property("kmongoVersion")}")
                implementation("no.tornado:tornadofx:${property("tornadoFXVersion")}")


            }

        }
        val jvmTest by getting {
            dependencies {
                implementation("ca.uhn.hapi.fhir:org.hl7.fhir.validation:${property("fhirCoreVersion")}")
                implementation("ca.uhn.hapi.fhir:org.hl7.fhir.utilities:${property("fhirCoreVersion")}")

                implementation("org.junit.jupiter:junit-jupiter:${property("junitVersion")}")
                implementation("org.junit.jupiter:junit-jupiter-engine:${property("junitVersion")}")
                implementation("org.junit.jupiter:junit-jupiter-api:${property("junitVersion")}")
                implementation("org.junit.jupiter:junit-jupiter-params:${property("junitVersion")}")

                implementation("io.ktor:ktor-server-tests:${property("ktorVersion")}")
                implementation("io.ktor:ktor-server-test-host:${property("ktorVersion")}")
                implementation("io.mockk:mockk:${property("mockk_version")}")
                implementation("io.ktor:ktor-client-mock:${property("ktorVersion")}")
                implementation("io.insert-koin:koin-test:${property("koinVersion")}")
            }

        }
        val jsMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-core:${property("ktorVersion")}")

                implementation("io.ktor:ktor-client-js:${property("ktorVersion")}") //include http&websockets
                implementation("io.ktor:ktor-client-json-js:${property("ktorVersion")}")
                implementation("io.ktor:ktor-client-content-negotiation:${property("ktorVersion")}")
                implementation("io.ktor:ktor-client-serialization-js:${property("ktorVersion")}")

                implementation("org.jetbrains.kotlinx:kotlinx-html-js:${property("kotlinxHtmlVersion")}")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${property("kotlinxCoroutinesVersion")}")
                implementation("io.ktor:ktor-serialization-kotlinx-json:${property("ktorVersion")}")

                implementation(project.dependencies.enforcedPlatform("org.jetbrains.kotlin-wrappers:kotlin-wrappers-bom:${property("kotlinWrappersVersion")}"))

                implementation("org.jetbrains.kotlin-wrappers:kotlin-react")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-router-dom")

                implementation("org.jetbrains.kotlin-wrappers:kotlin-mui")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-mui-icons")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-emotion")

                implementation("org.jetbrains.kotlin-wrappers:kotlin-css")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-styled")

                implementation(npm("node-polyglot", "2.4.0"))

                implementation(npm("react-ace", "9.5.0"))
                implementation(npm("ace-builds","^1.4.13" ))
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
    }
}

// withJava() bridges jvmMain and the Java `main` source set, causing java.main.resources
// to point at src/jvmMain/resources — the same directory the KMP plugin already registers
// for jvmProcessResources. Clear it to prevent the double-registration.
sourceSets.main.get().resources.setSrcDirs(emptyList<File>())

javafx {
    version = "14"
    modules("javafx.controls", "javafx.graphics", "javafx.web")
}

/**
 * Utility function to retrieve the current version number.
 */
task("printVersion") {
    doLast {
        println(project.version)
    }
}



tasks.withType<Jar> {
    setProperty("zip64", true)
    duplicatesStrategy = DuplicatesStrategy.INCLUDE

    manifest {
        attributes["Main-Class"] = "ServerKt"
        exclude("META-INF/*.SF",
            "META-INF/*.DSA",
            "META-INF/*.RSA"
        )
    }
    // To add all of the dependencies otherwise a "NoClassDefFoundError" error
    from(sourceSets.main.get().output)

    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get()
            .filter { it.name.endsWith("jar") }
            .map { zipTree(it) }
    })
}

tasks.named<Copy>("jvmProcessResources") {
    dependsOn("copySemver")
    duplicatesStrategy = DuplicatesStrategy.WARN
}

tasks.named<Test>("jvmTest") {
    useJUnitPlatform()
}

application {
    mainClass.set("ServerKt")
}

// Kotlin 1.9.10: webpack tasks read from the shared packages directory written by both
// development and production compileSync tasks. Declare the cross-dependencies explicitly
// so Gradle 8.0's implicit-dependency validation passes.
tasks.named("jsBrowserProductionWebpack").configure { dependsOn("jsDevelopmentExecutableCompileSync") }
tasks.named("jsBrowserDevelopmentWebpack").configure { dependsOn("jsProductionExecutableCompileSync") }

// include JS artifacts in any JAR we generate
tasks.named<Jar>("jvmJar").configure {
    dependsOn("jsBrowserDevelopmentWebpack")
    dependsOn("jsBrowserProductionWebpack")
    val taskName = if (project.hasProperty("isProduction")) {
        "jsBrowserProductionWebpack"
    } else {
        "jsBrowserDevelopmentWebpack"
    }
    val webpackTask = tasks.named<KotlinWebpack>(taskName)
    from(webpackTask.map { File(it.destinationDirectory, it.outputFileName) })
}

tasks.named("distZip").configure {
    setProperty("zip64", true)
    dependsOn("allMetadataJar", "jsJar")
}

tasks.named("distTar").configure {
    dependsOn("allMetadataJar", "jsJar")
}

tasks {
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        dependsOn("copySemver")
        kotlinOptions {
            jvmTarget = "17"
        }
    }

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

// Alias "installDist" as "stage" (for cloud providers)
tasks.register("stage") {
    dependsOn(tasks.named("installDist"))
}

tasks.named<JavaExec>("run").configure {
    classpath(tasks.named<Jar>("jvmJar")) // so that the JS artifacts generated by `jvmJar` can be found and served
}