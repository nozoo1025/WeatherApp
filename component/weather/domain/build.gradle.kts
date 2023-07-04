plugins {
    id("net.zuuno.jvm.library")
}

dependencies {
    implementation(project(":core:common"))
    implementation(libs.kotlinx.coroutines.core)
}