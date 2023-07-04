plugins {
    id("net.zuuno.android.library")
    id("net.zuuno.android.library.compose")
}

android {
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    namespace = "net.zuuno.core.deignsystem"
}

dependencies {
    api(libs.androidx.compose.material.iconsExtended)
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.runtime)
    api(libs.androidx.compose.ui.tooling.preview)
    api(libs.androidx.compose.ui.util)

    implementation(libs.androidx.core.ktx)
    implementation(libs.coil.kt.compose)
}