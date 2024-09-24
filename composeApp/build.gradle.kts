import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)

    alias(libs.plugins.kotlin.serialization)

    id("androidx.room") version "2.7.0-alpha07"
    id("com.google.devtools.ksp") version "2.0.0-1.0.22" //ksp for room annotation processing

}

kotlin {
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        moduleName = "composeApp"
        browser {
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                outputFileName = "composeApp.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(projectDirPath)
                    }
                }
            }
        }
        binaries.executable()
    }
    
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    jvm("desktop")
    // Room step6 part1 for adding ksp src directory to use AppDatabase::class.instantiateImpl() in iosMain:
    // Due to https://issuetracker.google.com/u/0/issues/342905180
    sourceSets.commonMain {
        kotlin.srcDir("build/generated/ksp/metadata")
    }
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    sourceSets {
        val desktopMain by getting
        
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)

            implementation(libs.ktor.client.okhttp)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            implementation(libs.navigation.compose)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)

            implementation("androidx.room:room-runtime:2.7.0-alpha07")
            implementation(libs.androidx.sqlite.bundled)

            api(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)

            implementation(libs.bundles.ktor)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)

            implementation(libs.ktor.client.okhttp)
        }
    }
}

android {
    namespace = "com.joeybasile.knowledgemanagement"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.joeybasile.knowledgemanagement"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
    dependencies {
        debugImplementation(compose.uiTooling)
    }
}

compose.desktop {
    application {
        mainClass = "com.joeybasile.knowledgemanagement.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.joeybasile.knowledgemanagement"
            packageVersion = "1.0.0"
        }
    }
}

//Room step3: path where we want to generate the schemas
room {
    schemaDirectory("$projectDir/schemas")
}

//Room step5  KSP For processing Room annotations , Otherwise we will get Is Room annotation processor correctly configured? error
dependencies {

    implementation(project(":composeApp"))
    implementation(project(":composeApp"))
    // Update: https://issuetracker.google.com/u/0/issues/342905180
    add("kspCommonMainMetadata", "androidx.room:room-compiler:2.7.0-alpha05")

}

//Room step6 part 2 make all source sets to depend on kspCommonMainKotlinMetadata:  Update: https://issuetracker.google.com/u/0/issues/342905180
tasks.withType<org.jetbrains.kotlin.gradle.dsl.KotlinCompile<*>>().configureEach {
    if (name != "kspCommonMainKotlinMetadata" ) {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}
tasks.register("buildAll") {
    dependsOn("assembleRelease")
    dependsOn("packageReleaseExe")
// macOSdependsOn(":desktopApp:packageReleaseDmg")
    //linux./gradlew :desktopApp:packageReleaseAppImage    // for Linux
}



