import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.palantir.gradle.gitversion.VersionDetails
import groovy.lang.Closure

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.palantir.gitVersion)
    id("maven-publish")
}

kotlin {
    explicitApi()

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }

        publishLibraryVariants("release", "debug")
    }

    jvm()

    iosArm64()
    iosSimulatorArm64()
    iosX64()

    macosArm64()
    macosX64()

//    linuxArm64()
    linuxX64()

    sourceSets {
        commonMain.dependencies {
            api(libs.ktor.client.core)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        androidMain.dependencies {
            api(libs.ktor.client.okhttp)
        }

        jvmMain.dependencies {
            api(libs.ktor.client.cio)
        }

        appleMain.dependencies {
            api(libs.ktor.client.darwin)
        }

        linuxMain.dependencies {
            api(libs.ktor.client.curl)
        }
    }
}

android {
    namespace = "kmp.common.ktor"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

fun versionString(): String {
    val versionDetails: Closure<VersionDetails> by extra

    try {
        with(versionDetails()) {
            listOfNotNull(
                lastTag,
                commitDistance.takeIf { commitDistance > 0 },
                gitHash.takeIf { commitDistance > 0 }
            ).joinToString("-")
        }
    } catch (e: Throwable) {
        "0.1"
    }.also {
        println("versionString: $it")
    }

    return "0.1-SNAPSHOT-1"
}

group = "kmp.common"
version = versionString()

private fun username(project: Project) =
    gradleLocalProperties(project.rootDir).getProperty("gpr.user") ?: System.getenv("GITHUB_USER")

private fun password(project: Project) =
    gradleLocalProperties(project.rootDir).getProperty("gpr.token") ?: System.getenv("GITHUB_TOKEN")

publishing {
    repositories {
        maven("https://maven.pkg.github.com/kotlin-multiplatform/common") {
            credentials {
                username = username(project)
                password = password(project)
            }
        }
    }
}
