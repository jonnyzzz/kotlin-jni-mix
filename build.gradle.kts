plugins {
  kotlin("multiplatform") version "1.3.61"
  id("me.filippov.gradle.jvm.wrapper") version "0.9.2"
}

repositories {
  mavenCentral()
}

kotlin {
  jvm()

  macosX64("native") {
    binaries {
      sharedLib()
    }

    compilations["main"].cinterops.create("jni") {
      val javaHome = File(System.getProperty("java.home")!!)
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

tasks.wrapper {
  distributionType = Wrapper.DistributionType.ALL
}

jvmWrapper {
    linuxJvmUrl = "https://d3pxv6yz143wms.cloudfront.net/8.232.09.1/amazon-corretto-8.232.09.1-linux-x64.tar.gz"
    macJvmUrl = "https://d3pxv6yz143wms.cloudfront.net/8.232.09.1/amazon-corretto-8.232.09.1-macosx-x64.tar.gz"
    windowsJvmUrl ="https://d3pxv6yz143wms.cloudfront.net/8.232.09.1/amazon-corretto-8.232.09.1-windows-x86-jdk.zip"
}
