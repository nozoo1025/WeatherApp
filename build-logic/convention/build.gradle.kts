import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "net.zuuno.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplicationCompose") {
            id = "net.zuuno.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidApplication") {
            id = "net.zuuno.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "net.zuuno.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "net.zuuno.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidFeature") {
            id = "net.zuuno.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("androidTest") {
            id = "net.zuuno.android.test"
            implementationClass = "AndroidTestConventionPlugin"
        }
        register("androidHilt") {
            id = "net.zuuno.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
        register("jvmLibrary") {
            id = "net.zuuno.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }
    }
}
