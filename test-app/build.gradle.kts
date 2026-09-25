import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.test)
}

android {
    namespace = "org.aa.ukraine.test.navigation"
    compileSdk = 37
    targetProjectPath = ":app"

    defaultConfig {
        minSdk = 24
        targetSdk = 37

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        aidl = false
        buildConfig = false
        shaders = false
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

dependencies {
    implementation(project(":app"))
    implementation(platform(libs.androidx.compose.bom))
    implementation(project(":core-data"))
    implementation(project(":core-testing"))
    implementation(project(":feature-main"))
    implementation(project(":feature-main-navigation"))

    implementation(libs.androidx.test.core)
    implementation(libs.androidx.compose.ui.test.junit4)
}
