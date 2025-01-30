import org.apache.tools.ant.property.LocalProperties
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp")
    kotlin("plugin.serialization") version "2.1.0"
}
val localProperties = Properties()
val localPropertiesFile = File(rootDir,"local.properties")
if (localPropertiesFile.exists() && localPropertiesFile.isFile){
    localPropertiesFile.inputStream().use {
        localProperties.load(it)
    }
}
android {
    namespace = "com.example.spendingtrackerapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.spendingtrackerapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String","API_KEY",localProperties.getProperty("key"))
        }
        debug {
            buildConfigField("String","API_KEY",localProperties.getProperty("key"))

        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig=true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //coil
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    // Koin core dependency
    implementation (libs.koin.core)

    // Koin for Android
    implementation (libs.koin.android)
    implementation(libs.koin.compose.viewmodel)
    // Koin for ViewModel (optional if using ViewModel)

    // Koin for navigation (optional if using Jetpack Navigation)
    //noinspection UseTomlInstead
    implementation ("io.insert-koin:koin-androidx-navigation:4.0.2")
    //room
    val room_version = "2.6.1"

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)

    // If this project uses any Kotlin source, use Kotlin Symbol Processing (KSP)
    // See Add the KSP plugin to your project
    ksp(libs.androidx.room.compiler)

    //navigation
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.navigation.compose)

    //retrofit
    // Retrofit for networking
    implementation(libs.retrofit)

    // Gson converter for Retrofit
    implementation(libs.converter.gson)
}