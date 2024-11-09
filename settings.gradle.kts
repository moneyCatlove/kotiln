pluginManagement {
    repositories {
        google() // Google 리포지토리
        mavenCentral() // Maven Central 리포지토리
        maven { url = uri("https://jitpack.io") } // JitPack 리포지토리
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS) // Project level에서 추가된 repositories를 허용하지 않음
    repositories {
        google() // Google 리포지토리
        mavenCentral() // Maven Central 리포지토리
        maven { url = uri("https://jitpack.io") } // JitPack 리포지토리
    }
}

rootProject.name = "SmartRing"
include(":app")
