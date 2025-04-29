plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.shreyash.androidarchitecturepatterns"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.shreyash.androidarchitecturepatterns"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(project(":mvc"))
    implementation(project(":mvp"))
    implementation(project(":mvvm"))
    implementation(project(":mvi"))
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
}