pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Lemon Wallet"
include(":app")
include(":core:ui")
include(":core:domain")
include(":features:auth")
include(":core:database:room")
include(":features:main-screen:storage-block")
include(":features:main-screen:last-operation-block")
include(":features:main-screen:chart-block")
include(":features:main-screen:main-screen")
include(":features:onbording-screen")
include(":core:datastore")
include(":core:navigation")
include(":features:detailstorage-screen")
include(":features:detailoperation-screen")
include(":features:categoryselection-screen")
include(":core:service")
include(":features:api-screen")
include(":features:ble-screen")
