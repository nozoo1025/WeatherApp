plugins {
    id("net.zuuno.android.library")
    id("net.zuuno.android.hilt")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "net.zuuno.component.weather.data"
}

dependencies {
    implementation(project(":component:weather:domain"))
    implementation(project(":core:common"))

    implementation(libs.room.runtime)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
}