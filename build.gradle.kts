plugins {
    alias(libs.plugins.android.application) apply false

    // Google 서비스 Gradle 플러그인 추가
    id("com.google.gms.google-services") version "4.4.2" apply false
}
