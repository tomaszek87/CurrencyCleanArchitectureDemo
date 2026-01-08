plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.tomasz.nbpcurrencies.feature.currency.di"
    compileSdk = libs.versions.compileSdk.get().toInt()
}

dependencies {
    implementation(project(":feature-currency:domain"))
    implementation(project(":feature-currency:data"))

    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)
}
