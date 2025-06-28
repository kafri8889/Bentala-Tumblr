import com.google.devtools.ksp.gradle.KspTaskJvm

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("idea")
    id("dagger.hilt.android.plugin")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.squareup.wire")
    id("com.google.devtools.ksp")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "com.anafthdev.bentalatumblr"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.anafthdev.bentalatumblr"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            kotlinOptions {
                freeCompilerArgs += listOf(
                    "-opt-in=kotlin.RequiresOptIn",
                    "-Xjvm-default=all"
                )
            }
        }
        debug {
            isMinifyEnabled = false
            isDebuggable = true

            kotlinOptions {
                freeCompilerArgs += listOf(
                    "-opt-in=kotlin.RequiresOptIn",
                    "-Xjvm-default=all"
                )
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

// Add this to fix ksp debug error when using wire and ksp
androidComponents {
    onVariants { variant ->
        // https://github.com/square/wire/issues/2335
        val buildType = variant.buildType.toString()
        val flavor = variant.flavorName.toString()
        tasks.withType<KspTaskJvm> {
            if (name.contains(buildType, ignoreCase = true) && name.contains(flavor, ignoreCase = true)) {
                dependsOn("generate${flavor.capitalize()}${buildType.capitalize()}Protos")
            }
        }
    }
}

// Atur task dependencies
afterEvaluate {
    tasks.filter { task ->
        task.name.startsWith("ksp") || task.name.startsWith("kapt")
    }.forEach { task ->
        task.dependsOn("generateProtos") // Generate Wire terlebih dahulu
    }
}

kapt {
    correctErrorTypes = true
}

wire {
    kotlin {
        android = true
    }
}

dependencies {

    implementation(project(mapOf("path" to ":datemodule")))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.animation)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material.icons)
    implementation(libs.androidx.material3)

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.serialization.json)
    kapt(libs.kotlinx.metadata.jvm)

    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.core.splashscreen)

    // Constraint layout
    implementation(libs.androidx.constraintlayout.compose)

    // Datastore
    implementation(libs.androidx.datastore)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.datastore.core)

    // Lifecycle
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.savedstate)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.extensions)
    kapt(libs.androidx.lifecycle.common.java8)

    // Dependency Injection
    implementation(libs.hilt.android)
    kapt(libs.androidx.hilt.compiler)
    kapt(libs.hilt.compiler)
    kapt(libs.hilt.android.compiler)

    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // Work Manager
    implementation(libs.androidx.hilt.work)
    implementation(libs.androidx.work.runtime.ktx)

    // Charting
    implementation(libs.compose)
    implementation(libs.compose.m3)
    implementation(libs.core)

    // Other
    implementation(libs.gson)
    implementation(libs.timber)
    implementation("com.squareup.wire:wire-runtime:4.9.2")
//    implementation("com.github.AppDevNext:AndroidChart:3.1.0.21")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
