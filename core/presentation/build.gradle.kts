plugins {
    id("net.zuuno.android.library")
}

android {
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    namespace = "net.zuuno.core.presentation"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.viewModelCompose)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.timber)
}