import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack

plugins {
    kotlin("multiplatform") version "1.4.0"
    kotlin("plugin.serialization") version "1.4.0"
    id ("org.openjfx.javafxplugin") version "0.0.8"
    application
}
group = "org.hl7.fhir"
version = "1.0-SNAPSHOT"

repositories {
    google()
    jcenter()
    //mavenLocal()
    mavenCentral()
    maven {
        url = uri("https://jitpack.io")
    }
    maven {
        url = uri("https://dl.bintray.com/kotlin/ktor")
    }
    maven {
        url = uri("https://dl.bintray.com/kotlin/kotlinx")
    }
    maven {
        url = uri("https://dl.bintray.com/kotlin/kotlin-js-wrappers")
    }
    maven {
        url = uri("https://oss.sonatype.org/content/groups/public/")
    }
    maven {
        url = uri("https://plugins.gradle.org/m2/")
    }
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        withJava()
    }
    js(){
        useCommonJs()
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
                implementation("com.fasterxml.jackson.core:jackson-databind:${property("jacksonVersion")}")

                implementation ("org.jetbrains.kotlinx:kotlinx-serialization-core:${property("serializationVersion")}")
                implementation ("org.jetbrains.kotlinx:kotlinx-serialization-protobuf:${property("serializationVersion")}")
                implementation("ca.uhn.hapi.fhir:org.hl7.fhir.validation:${property("fhirCoreVersion")}")
                implementation("ca.uhn.hapi.fhir:org.hl7.fhir.utilities:${property("fhirCoreVersion")}")
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
                implementation("io.ktor:ktor-server-netty:${property("ktorVersion")}")
                implementation("io.ktor:ktor-html-builder:${property("ktorVersion")}")
                implementation("io.ktor:ktor-server-jetty:${property("ktorVersion")}")
                implementation("io.ktor:ktor-server-core:${property("ktorVersion")}")
                implementation("io.ktor:ktor-websockets:${property("ktorVersion")}")
                implementation("io.ktor:ktor-jackson:${property("ktorVersion")}")

                implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:${property("kotlinxVersion")}")

                implementation("ch.qos.logback:logback-classic:1.2.3")
                implementation("org.litote.kmongo:kmongo-coroutine-serialization:3.12.2")
                implementation("no.tornado:tornadofx:${property("tornadoFXVersion")}")
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("io.ktor:ktor-server-tests:${property("ktorVersion")}")
            }
        }
        val jsMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-js:${property("ktorVersion")}") //include http&websockets
                implementation("io.ktor:ktor-client-json-js:${property("ktorVersion")}")
                implementation("io.ktor:ktor-client-serialization-js:${property("ktorVersion")}")

                implementation("org.jetbrains:kotlin-react:${property("kotlinReactVersion")}")
                implementation("org.jetbrains:kotlin-react-dom:${property("kotlinReactVersion")}")
                implementation("org.jetbrains:kotlin-react-router-dom:${property("kotlinReactRouterVersion")}")
                implementation("org.jetbrains:kotlin-styled:${property("kotlinStyledVersion")}")
                implementation("org.jetbrains:kotlin-react-redux:${property("kotlinReactReduxVersion")}")

                implementation("org.jetbrains.kotlinx:kotlinx-html-js:${property("kotlinxVersion")}")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${property("kotlinxCoroutinesVersion")}")

                implementation(npm("core-js", "${property("npm_core_js_version")}"))
                implementation(npm("react", "${property("npm_react_version")}"))
                implementation(npm("react-dom", "${property("npm_react_version")}"))
                implementation(npm("redux", "${property("npm_redux_version")}"))
                implementation(npm("react-redux", "${property("npm_react_redux_version")}"))
                implementation(npm("react-router-dom", "${property("npm_react_router_dom_version")}"))
                implementation(npm("styled-components", "${property("npm_styled_components_version")}"))
                implementation(npm("inline-style-prefixer", "${property("npm_inline_styled_prefixer_version")}"))

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
    version = "11.0.2"
    modules("javafx.controls", "javafx.graphics", "javafx.web")
}
application {
    mainClassName = "ServerKt"
}
tasks.getByName<KotlinWebpack>("jsBrowserProductionWebpack") {
    outputFileName = "output.js"
}
tasks.getByName<Jar>("jvmJar") {
    dependsOn(tasks.getByName("jsBrowserProductionWebpack"))
    val jsBrowserProductionWebpack = tasks.getByName<KotlinWebpack>("jsBrowserProductionWebpack")
    from(File(jsBrowserProductionWebpack.destinationDirectory, jsBrowserProductionWebpack.outputFileName))
}
tasks.getByName<JavaExec>("run") {
    dependsOn(tasks.getByName<Jar>("jvmJar"))
    classpath(tasks.getByName<Jar>("jvmJar"))
}