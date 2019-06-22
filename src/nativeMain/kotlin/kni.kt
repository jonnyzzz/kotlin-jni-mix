package org.jonnyzzz.jni.native

import org.jonnyzzz.jni.JNI_VERSION_9


@CName("JNI_OnLoad")
fun onLoad(vm: kotlinx.cinterop.CValuesRef<org.jonnyzzz.jni.JavaVMVar>?,
               reserved: kotlinx.cinterop.CValuesRef<*>?): org.jonnyzzz.jni.jint {
  println("JNI library is here!")
  return JNI_VERSION_9
}

@CName("JNI_OnUnload")
fun onUnload(vm: kotlinx.cinterop.CValuesRef<org.jonnyzzz.jni.JavaVMVar>?,
             reserved: kotlinx.cinterop.CValuesRef<*>?) {
  println("JNI library is unloaded!")
}

