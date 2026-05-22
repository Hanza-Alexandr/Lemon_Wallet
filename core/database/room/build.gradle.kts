plugins {
    alias(libs.plugins.android.library)
    id("com.google.devtools.ksp") // Инструмент для кодогенирации вместо kapt
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.room"
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
    implementation(libs.kotlinx.datetime)

    implementation(project(":core:domain"))
    //Room
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    ksp(libs.androidx.room.compiler) // Room Кодогенератор
    implementation(libs.androidx.room.ktx) // Доп для корутин и потоков
    implementation(libs.androidx.room.runtime)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}