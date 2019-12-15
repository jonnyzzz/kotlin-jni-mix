package org.jonnyzzz.jni.java


fun main() {
  requireNotNull(System.getProperty("jonnyzzz.demo")) {
    "Please run this example via the `run` task in " +
            "Gradle to make sure the native part is included correctly"
  }

  Runtime.getRuntime().loadLibrary("kotlin_jni_mix")

  val ret = NativeHost().callInt(42)
  println("ret from the native: $ret")
}

class NativeHost {
  external fun callInt(x: Int) : Int
}
