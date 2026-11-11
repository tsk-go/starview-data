plugins {
  id("com.android.application")
  id("org.jetbrains.kotlin.android")
}

android {
  // Resource IDs as in the original 2.84.1 APK (its compiled code has them as plain numbers): the hybrid build
  // (tools/hybrid) takes that code for every class we didn't change on purpose.
  androidResources { additionalParameters += listOf("--stable-ids", file("stable-ids.txt").absolutePath) }
  namespace = "com.starpilot.starview"
  compileSdk = 35

  defaultConfig {
    applicationId = "com.starpilot.starview"
    minSdk = 29
    targetSdk = 35
    versionCode = 307
    versionName = "2.86.0"
    // English only: the libraries' translations (appcompat, Play services) took ~300 KB of resources.arsc, which must be
    // stored uncompressed - the room 2.25.0 needed to stay under the 30 MiB send limit
    resourceConfigurations += listOf("en")
    ndk { abiFilters += listOf((project.findProperty("abi") as String?) ?: "arm64-v8a") }   // the tablet is arm64 (drops ~30 MB of other-ABI libs); -Pabi=x86_64 for the PC emulator
  }
  // One fixed key for every build since 2.17.0, so each version installs over the last one. The key is NOT in this
  // public repo: it comes from STARVIEW_KEYSTORE (a path; GitHub's build writes it from the repo secret
  // STARVIEW_KEYSTORE_B64) or keystore/starview.jks next to this project (git-ignored).
  signingConfigs {
    create("starview") {
      storeFile = (System.getenv("STARVIEW_KEYSTORE")?.let { file(it) } ?: rootProject.file("keystore/starview.jks"))
      storePassword = "starview"; keyAlias = "starview"; keyPassword = "starview"
    }
  }
  buildTypes {
    release { isMinifyEnabled = false; signingConfig = signingConfigs.getByName("starview") }
    debug { isMinifyEnabled = false; signingConfig = signingConfigs.getByName("starview") }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
  kotlinOptions { jvmTarget = "17" }
  // models are read into memory (not mapped), so they can be stored compressed
  packaging { resources.excludes += setOf("META-INF/*", "kotlin/**", "DebugProbesKt.bin"); dex.useLegacyPackaging = true; jniLibs.useLegacyPackaging = true }   // compressed dex / .so: keeps the APK under 30 MB
}

dependencies {
  implementation("org.capnproto:runtime:0.1.16")
  implementation("com.squareup.okhttp3:okhttp:4.12.0")
  implementation("androidx.appcompat:appcompat:1.7.0")
  implementation("androidx.core:core-ktx:1.13.1")
  implementation("com.google.zxing:core:3.5.3")
  // first-time pairing: scan the comma's QR code with Google's code scanner (no camera permission needed)
  implementation("com.google.android.gms:play-services-code-scanner:16.1.0")
  testImplementation("junit:junit:4.13.2")
  testImplementation("org.json:json:20240303")   // real org.json for JVM tests (Android's is a stub there)
  // on-tablet object detector (YOLOX-S) on the tablet GPU
  // Scene view (3D road + cars) on the tablet GPU
  implementation("com.google.android.filament:filament-android:1.75.1")
  implementation("com.google.android.filament:gltfio-android:1.75.1")
  implementation("com.google.android.filament:filament-utils-android:1.75.1")
  implementation("org.tensorflow:tensorflow-lite:2.16.1")
  implementation("org.tensorflow:tensorflow-lite-gpu:2.16.1")
  implementation("org.tensorflow:tensorflow-lite-gpu-api:2.16.1")  // GpuDelegateFactory$Options lives here
}

// The Java here was recovered from Kotlin bytecode, which has no checked exceptions: a few exception classes are made
// unchecked for compiling only (compile-stubs/README.md). Nothing from compile-stubs is packaged. (Android's org.json is
// in the java.base of the system image the build compiles against, so it is patched there too.)
val stubsBase = layout.buildDirectory.dir("compile-stubs/java.base")
val compileStubsBase by tasks.registering(JavaCompile::class) {
  source = fileTree("compile-stubs/java.base") { include("**/*.java") }
  classpath = files()
  destinationDirectory.set(stubsBase)
  sourceCompatibility = "17"; targetCompatibility = "17"
  options.compilerArgs.addAll(listOf("--patch-module", "java.base=" + file("compile-stubs/java.base").path))
}
tasks.withType<JavaCompile>().matching { it.name.endsWith("JavaWithJavac") }.configureEach {
  dependsOn(compileStubsBase)
  options.compilerArgs.addAll(listOf("-Xmaxerrs", "10000", "-nowarn", "-XDshould-stop.ifError=FLOW", "--patch-module", "java.base=" + stubsBase.get().asFile.path))
}
