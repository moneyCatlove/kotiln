pluginManagement {
    repositories {
        google() // Google Maven Repository
        mavenCentral() // Maven Central Repository
        maven { url = uri("https://jitpack.io") } // JitPack Repository
    }
    plugins {
        id("com.android.application") version "8.1.0" // Android Application Plugin
        id("com.android.library") version "8.1.0" // Android Library Plugin
        id("org.jetbrains.kotlin.android") version "1.9.0" // Kotlin Plugin
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS) // 하위 모듈의 리포지토리 추가를 금지
    repositories {
        google() // Google Maven Repository
        mavenCentral() // Maven Central Repository
        maven { url = uri("https://jitpack.io") } // JitPack Repository
    }
}

rootProject.name = "SmartRing" // 프로젝트 이름
include(":app") // app 모듈 포함
