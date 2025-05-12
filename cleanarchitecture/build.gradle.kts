plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.shreyash.cleanarchitecture"
    compileSdk = 35

    defaultConfig {
        minSdk = 28
        targetSdk = 35

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {
    implementation(project(":common"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    // Add Espresso-Intents for intent testing
    androidTestImplementation("androidx.test.espresso:espresso-intents:${libs.versions.espressoCore.get()}")
    // Add Espresso idling resources for async operations
    androidTestImplementation("androidx.test.espresso:espresso-idling-resource:${libs.versions.espressoCore.get()}")
    // Add Espresso contrib for RecyclerView testing
    androidTestImplementation("androidx.test.espresso:espresso-contrib:${libs.versions.espressoCore.get()}")
    // Add Mockito for mocking in tests
    androidTestImplementation("org.mockito:mockito-android:5.0.0")
    // Add Core testing for LiveData testing
    androidTestImplementation("androidx.arch.core:core-testing:2.2.0")
    androidTestImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.9.0")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")

}