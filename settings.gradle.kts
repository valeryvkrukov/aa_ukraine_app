pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "AA Ukraine"

include(":app")
include(":core-data")
include(":core-database")
include(":core-testing")
include(":core-ui")

include(":feature-main-navigation")
include(":feature-main")

include(":feature-map-navigation")
include(":feature-map")

include(":feature-schedule-navigation")
include(":feature-schedule")

include(":feature-diary-navigation")
include(":feature-diary")

include(":feature-assistant-navigation")
include(":feature-assistant")

include(":test-app")
