import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile
import org.jetbrains.kotlin.gradle.targets.js.ir.KotlinJsIrCompilation
import org.jetbrains.kotlin.gradle.targets.js.npm.npmProject
import org.jetbrains.kotlin.incremental.deleteDirectoryContents

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kmpNativeCoroutines)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
        }
    }

    js(IR) {
        moduleName = "kmp-shared"
        generateTypeScriptDefinitions()
        nodejs()
        binaries.executable()
        useEsModules()
        // Enables ES2015 classes generation
        compilerOptions {
            useEsClasses = true
        }

        // Copy Kotlin/JS to harmonyApp
        tasks.getByName("jsDevelopmentExecutableCompileSync") {
            doLast {
                val sourceDir = File("build/compileSync/js/main/developmentExecutable", "kotlin")
                println("sourceDir: $sourceDir")
                val destDir = File("../harmonyApp/entry/src/main/kotlinjs").also {
                    if(it.exists()) {
                        it.deleteDirectoryContents()
                    } else {
                        it.mkdirs()
                    }
                }
                println("destDir: $destDir")
                copy {
                    from(sourceDir)
                    into(destDir)
                }
                destDir.listFiles()?.forEach {
                    if(it.name.endsWith(".mjs")) {
                        val lines = it.readLines()
                        val newLines = ArrayList<String>(lines.size)
                        var importDone = false
                        for (line in lines) {
                            if (line.startsWith("//# sourceMappingURL=")) {
                                newLines += line.replace(".mjs.map", ".js.map")
                            } else if (importDone) {
                                newLines += line
                            } else if (line.startsWith("//region block")) {
                                importDone = true
                                newLines += line
                            } else {
                                newLines += line.replace(Regex("""'(\./.+)\.mjs'""")) { "'${it.groupValues[1]}.js'" }
                            }
                        }
                        it.writeText(newLines.joinToString("\n"))

                        it.renameTo(File(destDir, it.name.replace(".mjs", ".js")))
                    } else if(it.name.endsWith(".mjs.map")) {
                        it.renameTo(File(destDir, it.name.replace(".mjs.map", ".js.map")))
                    }
                }
            }
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
        jsMain.dependencies {

        }
        commonMain.dependencies {
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.koin.core)
            api(libs.kmp.observable.viewmodel)
        }

        // Required by KMM-ViewModel
        all {
            languageSettings.optIn("kotlinx.cinterop.ExperimentalForeignApi")
            languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
            languageSettings.optIn("kotlin.js.ExperimentalJsExport")
        }
    }
}

android {
    namespace = "com.jetbrains.kmpapp.shared"
    compileSdk = 35
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = 24
    }
}
