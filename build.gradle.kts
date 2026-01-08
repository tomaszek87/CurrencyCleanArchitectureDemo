import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.ktlint) apply false
}

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    plugins.withId("org.jetbrains.kotlin.android") {
        extensions.configure<KotlinProjectExtension> {
            jvmToolchain(17)
        }
    }
    plugins.withId("org.jetbrains.kotlin.jvm") {
        extensions.configure<KotlinProjectExtension> {
            jvmToolchain(17)
        }
    }
}
