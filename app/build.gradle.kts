plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.instagram.downloader"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.instagram.downloader"
        minSdk = 21
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation (libs.legacy.support.v4)
    implementation (libs.adapter.rxjava2)
    implementation (libs.converter.gson)
    implementation (libs.retrofit)
    implementation (libs.okhttp)
    implementation (libs.logging.interceptor)
    implementation (libs.rxandroid)
    implementation (libs.rxjava)
    implementation (libs.glide)
    implementation (libs.circleimageview)
    implementation (libs.lottie)

    implementation(project(":bubblenavigation"))
}