plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // For RoomDB
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.drawingapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.drawingapp"
        minSdk = 24
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
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures{
        viewBinding = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.material3.android)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.runtime.livedata)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation("com.google.android.material:material:1.11.0")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.fragment.ktx.v162)

    implementation("androidx.room:room-common:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")

    androidTestImplementation ("androidx.arch.core:core-testing:2.2.0")
    androidTestImplementation ("org.jetbrains.kotlin:kotlin-stdlib: 1.7.20")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.arch.core:core-testing:2.2.0")
    androidTestImplementation ("androidx.test:runner:1.4.0")
    androidTestImplementation ("androidx.test:rules:1.6.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.6.1")
    androidTestImplementation("androidx.test.espresso:espresso-intents:3.6.1")
    androidTestImplementation("androidx.test.espresso:espresso-accessibility:3.5.1")
    androidTestImplementation("androidx.test.espresso:espresso-web:3.6.1")
    androidTestImplementation("androidx.test.espresso.idling:idling-concurrent:3.6.1")
    androidTestImplementation("androidx.test.espresso:espresso-idling-resource:3.6.1")

}