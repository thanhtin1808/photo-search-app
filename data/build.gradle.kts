plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.era.photosearch.data"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

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
            buildConfigField ("String", "API_KEY", "\"SzPsGE3Aqnve3eDlLxAukeABsCkhYKYiQV5UaKLSx2Pv1q9Z1CXC77wA\"")

            buildConfigField(
                type = "String",
                name = "BASE_URL",
                value = "\"https://api.pexels.com\"",
            )
        }

        debug {
            buildConfigField ("String", "API_KEY", "\"SzPsGE3Aqnve3eDlLxAukeABsCkhYKYiQV5UaKLSx2Pv1q9Z1CXC77wA\"")

            buildConfigField(
                type = "String",
                name = "BASE_URL",
                value = "\"https://api.pexels.com\"",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(":domain"))

    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    implementation(libs.room.runtime)
    //noinspection KaptUsageInsteadOfKsp
    kapt(libs.room.compiler)

    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp3.logging)
    implementation(libs.gson)

    implementation(libs.security.crypto.datastore)
    implementation(libs.security.crypto.datastore.preferences)

    implementation(libs.timber)

    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotest)
    testImplementation(libs.turbine)
    testImplementation(libs.kotlinx.coroutines.test)
}