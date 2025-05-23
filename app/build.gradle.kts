plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.gms.google-services")
    id ("com.google.protobuf") version "0.9.1"
    kotlin("plugin.serialization") version "2.0.21"
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0"
    id("androidx.room")
    id("com.google.devtools.ksp")

}

android {
    namespace = "com.gravity.billeasy"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.gravity.billeasy"
        minSdk = 24
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
    room {
        schemaDirectory("$projectDir/schemas")
    }
    protobuf {
        protoc {
            artifact = "com.google.protobuf:protoc:3.24.1"
        }
        generateProtoTasks {
            all().forEach { task->
                task.builtins {
                    create("java") {
                        option("lite")
                    }
                }
            }
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //proto datastore
    implementation  (libs.protobuf.javalite)

    //firebase dependencies
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)

    //compose navigation dependency
    val navVersion = "2.8.3"
    implementation(libs.androidx.navigation.compose)

    // datastore dependency
    implementation (libs.androidx.datastore.preferences)
    implementation(libs.androidx.datastore.preferences.core)

    //lottie animation
    implementation (libs.lottie.compose)

    //serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")

    // material dependency for bottom navigation
    implementation (libs.androidx.material)

    // room dependency
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    ksp("androidx.room:room-compiler:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")

    //theme
    implementation (libs.material)
    implementation (libs.androidx.appcompat)

    // paging dependency
    implementation (libs.androidx.paging.runtime)
    implementation (libs.androidx.paging.compose)

    // gson dependency for serialization
    implementation ("com.google.code.gson:gson:2.10.1")



}