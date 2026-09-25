import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.hilt.gradle)
    alias(libs.plugins.ksp)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "org.aa.ukraine"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "org.aa.ukraine"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "org.aa.ukraine.core.testing.HiltTestRunner"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
        aidl = false
        buildConfig = false
        shaders = false
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

// Enable room auto-migrations
ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}

// Migrate from kotlinOptions to compilerOptions
kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

dependencies {
    implementation(project(":core-ui"))
    implementation(project(":core-database"))
    implementation(project(":core-data"))
    implementation(project(":feature-main"))
    implementation(project(":feature-main-navigation"))
    implementation(project(":feature-schedule"))
    implementation(project(":feature-schedule-navigation"))
    implementation(libs.androidx.appcompat)


    // Core Android dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    // Hilt Dependency Injection
    implementation(libs.hilt.android)
    listOf("ksp", "kspAndroidTest", "kspTest").forEach { config ->
        dependencies.add(config, libs.hilt.compiler)
    }

    // Arch Components
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Compose
    listOf("implementation", "androidTestImplementation").forEach { config ->
        dependencies.add(config, platform(libs.androidx.compose.bom))
    }
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    // Splash Screen
    implementation(libs.androidx.core.splashscreen)

    // Tooling
    debugImplementation(libs.androidx.compose.ui.tooling)

    // Instrumented tests
    androidTestImplementation(project(":core-testing"))
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
}
