plugins {
  id("com.android.application")
  id("org.jetbrains.kotlin.android")
}

// StarLock: the phone's Lock / Unlock for the van (Bluetooth LE to the comma's ble_door, else Wi-Fi to starviewd).
android {
  namespace = "com.starpilot.starlock"
  compileSdk = 35

  defaultConfig {
    applicationId = "com.starpilot.starlock"
    minSdk = 29
    targetSdk = 35
    versionCode = 31
    versionName = "2.19.1"
    resourceConfigurations += listOf("en")
  }
  // Same key as StarView (see app/build.gradle.kts): installs over the StarLock already on the phone.
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
  packaging { resources.excludes += setOf("META-INF/*", "kotlin/**", "DebugProbesKt.bin") }
}

dependencies {
  implementation("com.squareup.okhttp3:okhttp:4.12.0")
  // pairing: scan the comma's QR code with Google's code scanner (no camera permission needed)
  implementation("com.google.android.gms:play-services-code-scanner:16.1.0")
  // hotspot / Bluetooth switching through Shizuku
  implementation("dev.rikka.shizuku:api:13.1.5")
  implementation("dev.rikka.shizuku:provider:13.1.5")
  implementation("org.lsposed.hiddenapibypass:hiddenapibypass:4.3")
}

// Recovered from bytecode like the app module: the same compile-only exception stubs (../app/compile-stubs).
val stubsBase = layout.buildDirectory.dir("compile-stubs/java.base")
val stubsSrc = rootProject.file("app/compile-stubs/java.base")
val compileStubsBase by tasks.registering(JavaCompile::class) {
  source = fileTree(stubsSrc) { include("**/*.java") }
  classpath = files()
  destinationDirectory.set(stubsBase)
  sourceCompatibility = "17"; targetCompatibility = "17"
  options.compilerArgs.addAll(listOf("--patch-module", "java.base=" + stubsSrc.path))
}
tasks.withType<JavaCompile>().matching { it.name.endsWith("JavaWithJavac") }.configureEach {
  dependsOn(compileStubsBase)
  options.compilerArgs.addAll(listOf("-Xmaxerrs", "10000", "-nowarn", "-XDshould-stop.ifError=FLOW", "--patch-module", "java.base=" + stubsBase.get().asFile.path))
}
