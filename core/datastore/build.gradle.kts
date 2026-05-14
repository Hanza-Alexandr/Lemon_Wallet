plugins {
    alias(libs.plugins.android.library)
    id("com.google.devtools.ksp") // Инструмент для кодогенирации вместо kapt
    id("com.google.dagger.hilt.android")

    kotlin("plugin.serialization")

}

android {
    namespace = "com.example.datastore"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {
    implementation(project(":core:domain"))


    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    // Hilt
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.hilt.android)
    implementation(libs.androidx.compose.runtime)
    ksp(libs.hilt.android.compiler)

    // Сериализация
    implementation(libs.kotlinx.serialization.json)

    // DataStore (библиотеки уже есть в модуле, но в app тоже могут пригодиться для DI)
    implementation(libs.androidx.datastore.preferences)


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}