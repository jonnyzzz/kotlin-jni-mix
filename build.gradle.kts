plugins {
  kotlin("multiplatform") version "1.3.61"
  id("me.filippov.gradle.jvm.wrapper") version "0.9.2"
}

repositories {
  mavenCentral()
}

val jvm = kotlin.jvm()

jvm.compilations["main"].dependencies {
  implementation(kotlin("stdlib-jdk8"))
}

val native = kotlin.macosX64("native") {
  binaries {
    sharedLib()
  }

  compilations["main"].cinterops.create("jni") {
    val javaHome = File(System.getProperty("java.home")!!)
    packageName = "org.jonnyzzz.jni"
    includeDirs(
            Callable { File(javaHome, "include") },
            Callable { File(javaHome, "include/darwin") },
            Callable { File(javaHome, "include/linux") },
            Callable { File(javaHome, "include/win32") }
    )
  }
}

val run by tasks.creating(JavaExec::class) {
  main = "org.jonnyzzz.jni.java.JvmKt"
  group = "application"

  dependsOn(jvm.compilations.map { it.compileAllTaskName })
  dependsOn(native.compilations.map { it.compileAllTaskName })
  dependsOn(native.binaries.map { it.linkTaskName })

  systemProperty("jonnyzzz.demo", "set")

  doFirst {
    classpath(
            kotlin.targets["jvm"].compilations["main"].output.allOutputs.files,
            configurations["jvmRuntimeClasspath"]
    )

    ///disable app icon on macOS
    systemProperty("java.awt.headless", "true")
    systemProperty("java.library.path", native.binaries.findSharedLib("debug")!!.outputDirectory)
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
