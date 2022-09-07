import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack

plugins {
    kotlin("multiplatform") version "1.5.20"
    kotlin("plugin.serialization") version "1.5.20"

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
        url = uri("https://oss.sonatype.org/content/repositories/snapshots")
    }
    maven {
        url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
    }
    maven {
        url = uri("https://plugins.gradle.org/m2/")
    }
}

kotlin {
    jvm {
        compilations {
            all {
                kotlinOptions.jvmTarget = "11"
            }
        }
        withJava()

        tasks.test { useJUnit() }
        testRuns["test"].executionTask.configure {
            useJUnit()
        }
    }
    js {
        useCommonJs()
        binaries.executable()
        browser {
            binaries.executable()
            webpackTask {
                cssSupport.enabled = true
            }
            runTask {
                cssSupport.enabled = true
            }
            testTask {
                useKarma {
                    useChromeHeadless()
                    webpackConfig.cssSupport.enabled = true
                }
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("org.jetbrains.kotlin:kotlin-reflect:1.5.0")
                implementation("com.fasterxml.jackson.core:jackson-databind:${property("jacksonVersion")}")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${property("serializationVersion")}")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:${property("serializationVersion")}")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-protobuf:${property("serializationVersion")}")
                implementation("ca.uhn.hapi.fhir:org.hl7.fhir.validation:${property("fhirCoreVersion")}")
                implementation("ca.uhn.hapi.fhir:org.hl7.fhir.utilities:${property("fhirCoreVersion")}")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation("ca.uhn.hapi.fhir:org.hl7.fhir.validation:${property("fhirCoreVersion")}")
                implementation("ca.uhn.hapi.fhir:org.hl7.fhir.utilities:${property("fhirCoreVersion")}")
            }
        }
        val jvmMain by getting {
            dependencies {
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
                implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:${property("kotlinxVersion")}")
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


                implementation("org.jetbrains.kotlin-wrappers:kotlin-react:${property("kotlinReactVersion")}")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:${property("kotlinReactVersion")}")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-router-dom:${property("kotlinReactRouterVersion")}")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-redux:${property("kotlinReactReduxVersion")}")

                implementation("org.jetbrains.kotlinx:kotlinx-html-js:${property("kotlinxVersion")}")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${property("kotlinxCoroutinesVersion")}")
                implementation("io.ktor:ktor-serialization-kotlinx-json:${property("ktorVersion")}")


                implementation(npm("core-js", "${property("npm_core_js_version")}"))
                implementation(npm("react", "${property("npm_react_version")}"))
                implementation(npm("react-dom", "${property("npm_react_version")}"))
                implementation(npm("redux", "${property("npm_redux_version")}"))
                implementation(npm("react-redux", "${property("npm_react_redux_version")}"))
                implementation(npm("react-router-dom", "${property("npm_react_router_dom_version")}"))

                //Styled Components
               /*implementation("org.jetbrains:kotlin-styled:${property("kotlinStyledVersion")}")
                implementation(npm("styled-components", "${property("npm_styled_components_version")}"))
                implementation(npm("inline-style-prefixer", "${property("npm_inline_styled_prefixer_version")}"))
                */
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-css:17.0.2-pre.298-kotlin-1.6.10")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-css:1.0.0-pre.325-kotlin-1.6.10")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-styled:5.3.3-pre.290-kotlin-1.6.10")
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
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    manifest {
        attributes["Main-Class"] = "ServerKt"
        exclude("META-INF/*.SF","META-INF/*.DSA", "META-INF/*.RSA")
    }
    // To add all of the dependencies otherwise a "NoClassDefFoundError" error
    from(sourceSets.main.get().output)

    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}

tasks.named<Copy>("jvmProcessResources") {
    duplicatesStrategy = DuplicatesStrategy.WARN
}

tasks.named<Test>("jvmTest") {
    useJUnitPlatform()
}

application {
    mainClass.set("ServerKt")
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

tasks {
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "11"
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
tasks.create("stage") {
    dependsOn(tasks.getByName("installDist"))
}

tasks.getByName<JavaExec>("run") {
    classpath(tasks.getByName<Jar>("jvmJar")) // so that the JS artifacts generated by `jvmJar` can be found and served
}