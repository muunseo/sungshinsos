plugins {
    id("com.android.application")
    id("com.google.gms.google-services") // Google services 플러그인 적용
}

android {
    namespace = "com.example.sungshinsos"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.sungshinsos"
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
            // release 서명 설정을 나중에 추가해야 합니다.
            // 현재는 debug 서명 설정으로 되어 있습니다.
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    packagingOptions {
        exclude("META-INF/INDEX.LIST")
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/*.kotlin_module")
        exclude("META-INF/*.pro")
    }
}

dependencies {
    // 기본 AndroidX 라이브러리
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // Firebase 라이브러리
    implementation(libs.firebase.auth)
    implementation(libs.firebase.database)
    implementation("com.google.firebase:firebase-messaging:23.0.0")

    // 테스트 관련 라이브러리
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // gRPC 및 Dialogflow 의존성 추가
    implementation("io.grpc:grpc-okhttp:1.51.0") // gRPC OkHttp 채널 제공자
    implementation("io.grpc:grpc-protobuf:1.51.0") // gRPC Protobuf
    implementation("io.grpc:grpc-stub:1.51.0") // gRPC Stub
    implementation("com.google.cloud:google-cloud-dialogflow:2.0.0") // Dialogflow 최신 버전
    implementation("com.google.auth:google-auth-library-oauth2-http:1.10.0") // 인증 관련 라이브러리 최신 버전

    // OkHttp 라이브러리 추가
    implementation("com.squareup.okhttp3:okhttp:4.10.0") // OkHttp 라이브러리
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0") // OkHttp 로깅 인터셉터

    // JSON 파싱을 위한 Gson 라이브러리 추가
    implementation("com.google.code.gson:gson:2.10.1")

    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
}
