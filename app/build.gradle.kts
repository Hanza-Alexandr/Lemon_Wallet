plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    kotlin("plugin.serialization")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.lemonwallet"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.example.lemonwallet"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    // Core modules
    implementation(project(":core:domain"))
    implementation(project(":core:ui"))
    implementation(project(":core:database:room"))
    implementation(project(":core:datastore"))
    implementation(project(":core:navigation"))


    // Feature modules
    implementation(project(":features:auth"))
    implementation(project(":features:onbording-screen"))
    implementation(project(":features:main-screen:main-screen"))
    implementation(project(":features:main-screen:chart-block"))
    implementation(project(":features:main-screen:storage-block"))
    implementation(project(":features:main-screen:last-operation-block"))
    implementation(project(":features:detailstorage-screen"))
    implementation(project(":features:detailoperation-screen"))
    implementation(project(":features:categoryselection-screen"))



    // Hilt
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.hilt.android)
    implementation(libs.androidx.compose.runtime)
    ksp(libs.hilt.android.compiler)

    // Иконки Material
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.material3)

    // Jetpack Navigation
    implementation(libs.androidx.navigation.compose)

    // Сериализация
    implementation(libs.kotlinx.serialization.json)

    // Встроенный SplashScreen
    implementation(libs.androidx.core.splashscreen)

    // DataStore (библиотеки уже есть в модуле, но в app тоже могут пригодиться для DI)
    implementation(libs.androidx.datastore.preferences)

    // Room
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.runtime)

    // Common Android/UI
    implementation(libs.material)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.foundation.layout)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
