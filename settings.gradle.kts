pluginManagement {
    repositories {
        maven {
            url  = uri ("https://repo.eclipse.org/content/repositories/paho-snapshots/")
        }
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
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven {
            url  = uri ("https://repo.eclipse.org/content/repositories/paho-snapshots/")
        }
        google()
        mavenCentral()
    }
}

rootProject.name = "Color Sorting Controller"
include(":app")
 