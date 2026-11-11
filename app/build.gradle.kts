plugins { id("com.android.application"); id("org.jetbrains.kotlin.android") }
android { namespace = "de.mi45.camerastudio"; compileSdk = 35
 defaultConfig { applicationId = "de.mi45.camerastudio"; minSdk = 26; targetSdk = 35; versionCode = 20001; versionName = "2.0.1" }
 compileOptions { sourceCompatibility = JavaVersion.VERSION_17; targetCompatibility = JavaVersion.VERSION_17 }
 kotlinOptions { jvmTarget = "17" }
}
