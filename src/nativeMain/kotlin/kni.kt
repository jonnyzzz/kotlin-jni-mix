package org.jonnyzzz.jni.native

import kotlinx.cinterop.CPointer
import org.jonnyzzz.jni.JNIEnvVar
import org.jonnyzzz.jni.jclass
import org.jonnyzzz.jni.jint

//NOTE: build the project is your see red code here
//NOTE: by calling the Gradle `build` task

@Suppress("UNUSED_PARAMETER")
@CName("Java_org_jonnyzzz_jni_java_NativeHost_callInt")
fun callInt(env: CPointer<JNIEnvVar>, clazz: jclass, it: jint): jint {
  initRuntimeIfNeeded()
  Platform.isMemoryLeakCheckerActive = false

  println("Native function is executed with: $it")
  return it + 1
}
