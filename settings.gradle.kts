pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Currency Demo"
include(":app")
include(":core:ui")
include(":feature-currency:domain")
include(":feature-currency:data")
include(":feature-currency:presentation")
include(":feature-currency:di")
