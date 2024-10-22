import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.multiplatform)
//    alias(libs.plugins.palantir.gitVersion)
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

//fun versionString(): String {
//    val versionDetails: groovy.lang.Closure<com.palantir.gradle.gitversion.VersionDetails> by extra
//
//    return try {
//        with(versionDetails()) {
//            listOfNotNull(
//                lastTag,
//                commitDistance.takeIf { commitDistance > 0 },
//                gitHash.takeIf { commitDistance > 0 }
//            ).joinToString("-")
//        }
//    } catch (e: Throwable) {
//        "0.1"
//    }.also {
//        println("versionString: $it")
//    }
//}

group = "kmp.common"
version = "0.1-SNAPSHOT-5" //versionString()

private fun username(project: Project) =
    gradleLocalProperties(project.rootDir, providers).getProperty("gpr.user") ?: System.getenv("GITHUB_USER")

private fun password(project: Project) =
    gradleLocalProperties(project.rootDir, providers).getProperty("gpr.token") ?: System.getenv("GITHUB_TOKEN")

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
