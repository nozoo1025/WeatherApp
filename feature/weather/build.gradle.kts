plugins {
    id("net.zuuno.android.feature")
    id("net.zuuno.android.library.compose")
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "net.zuuno.feature.weather"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:presentation"))
    implementation(project(":component:weather:data"))
    implementation(project(":component:weather:domain"))

    implementation(libs.kotlinx.collections.immutable)
}