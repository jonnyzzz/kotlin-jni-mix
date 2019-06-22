plugins {
  kotlin("multiplatform") version "1.3.40"
}

repositories {
  mavenCentral()
}


val javaHome = File(System.getProperty("java.home")!!)

kotlin {
  jvm()

  macosX64("native") {
    binaries {
      sharedLib()
    }

    compilations["main"].cinterops.create("jni") {
      packageName = "org.jonnyzzz.jni"
      includeDirs(
              Callable { File(javaHome, "include") },
              Callable { File(javaHome, "include/darwin") }
      )
    }
  }

  sourceSets["jvmMain"].dependencies {
    implementation(kotlin("stdlib-jdk8"))
  }
}
