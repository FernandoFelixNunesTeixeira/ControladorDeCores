plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.compose.compiler)
}

configurations.all {
    resolutionStrategy.dependencySubstitution {
        substitute(module("org.hamcrest:hamcrest-core:1.1"))
            .using(module("junit:junit:4.10"))
    }
}

android {
    namespace = "com.example.colorsortingcontroller"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.colorsortingcontroller"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    //PAHO - MQTT
    implementation ("org.eclipse.paho:org.eclipse.paho.client.mqttv3:1.2.2")
    implementation ("org.eclipse.paho:org.eclipse.paho.android.service:1.1.1")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.testng)
    implementation(libs.androidx.media3.common.ktx)
    implementation(libs.androidx.recyclerview)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //Biometrics
    implementation ("androidx.biometric:biometric-ktx:1.2.0-alpha05")

    //Material3 Icons
    implementation("androidx.compose.material:material-icons-extended:1.4.3")
    implementation ("androidx.compose.ui:ui-text:1.5.0")

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    //Retrofit with Scalar Converter
    //implementation("com.squareup.retrofit2:converter-scalars:2.9.0")
   // implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
    //implementation("com.squareup.okhttp3:okhttp:4.11.0")

    // Kotlin serialization
    //implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")

    //ViewModel
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")

    //Room
    implementation(libs.room)
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)
    ksp(libs.room.compiler)

    //Gson para manipular objetos json
    implementation("com.google.code.gson:gson:2.8.9")

    //Charts
    implementation(libs.charts)

}