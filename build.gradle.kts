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
    mavenLocal()
    mavenCentral()
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
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        withJava()
    }
    js(){
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
                implementation("ca.uhn.hapi.fhir:org.hl7.fhir.validation:${property("fhirCoreVersion")}")

                implementation ("org.jetbrains.kotlinx:kotlinx-serialization-core:${property("serializationVersion")}")
                implementation ("org.jetbrains.kotlinx:kotlinx-serialization-protobuf:${property("serializationVersion")}")
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
                implementation("ca.uhn.hapi.fhir:org.hl7.fhir.validation:${property("fhirCoreVersion")}")
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

                implementation("org.jetbrains.kotlinx:kotlinx-html-js:${property("kotlinxVersion")}")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${property("kotlinxCoroutinesVersion")}")

                implementation(npm("react", "${property("reactVersion")}"))
                implementation(npm("react-dom", "${property("reactVersion")}"))
                implementation(npm("react-router-dom", "5.1.2"))
                implementation(npm("styled-components", "4.4.1")) // Animations don't work with styled components 5+
                implementation(npm("inline-style-prefixer", "5.1.0"))
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