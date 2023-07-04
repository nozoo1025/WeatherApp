pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "KasaApuri"

include(":app")

include(":core:designsystem")
include(":core:common")

include(":feature:weather")
include(":component:weather:domain")
include(":component:weather:data")
include(":core:presentation")
