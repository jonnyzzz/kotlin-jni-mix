package org.jonnyzzz.jni.native

import kotlinx.cinterop.COpaquePointerVar
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.CValuesRef
import kotlinx.cinterop.alloc
import kotlinx.cinterop.cstr
import kotlinx.cinterop.get
import kotlinx.cinterop.invoke
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.pointed
import kotlinx.cinterop.ptr
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.staticCFunction
import kotlinx.cinterop.value
import org.jonnyzzz.jni.JNIEnv
import org.jonnyzzz.jni.JNIEnvVar
import org.jonnyzzz.jni.JNINativeMethod
import org.jonnyzzz.jni.JNI_OK
import org.jonnyzzz.jni.JNI_VERSION_9
import org.jonnyzzz.jni.JavaVMVar
import org.jonnyzzz.jni.jclass
import org.jonnyzzz.jni.jint


@Suppress("unused")
@CName("JNI_OnLoad")
fun onLoad(vm: CPointer<JavaVMVar>,
           reserved: CValuesRef<*>?): jint {
  println("JNI library is here!")

  val env = memScoped {
    //https://docs.oracle.com/javase/8/docs/technotes/guides/jni/spec/invocation.html#GetEnv
    val vmPtr = vm.getPointer(this)[0] ?: run {
      println("getPointer is null")
      error("getPointer is null")
    }

    val pptr = alloc<COpaquePointerVar> { value = null }
    val code = vmPtr.pointed.GetEnv!!.invoke(vm, pptr.ptr, JNI_VERSION_9)

    if (code != JNI_OK) {
      println("Code $code != $JNI_OK")
    }

    pptr.value!!.reinterpret<JNIEnvVar>()
  }

  println("Completed ENV pointer: $env")

  val clazz = memScoped {
    env.pointed.pointed!!.FindClass!!.invoke(env, "org/jonnyzzz/jni/java/NativeHost".cstr.ptr)
  }

  println("Completed Clazz pointer: $clazz")


  memScoped {
    env.pointed.pointed!!.RegisterNatives!!.invoke(env, clazz, alloc<JNINativeMethod> {
      this.name = "callInt".cstr.ptr
      this.signature = "(I)I".cstr.ptr
      this.fnPtr = staticCFunction<CPointer<JNIEnvVar>, jclass, jint, jint> { _,_, it -> 42 + it }
    }.ptr, 1)
  }
  println("Completed Clazz Natives")
  return JNI_VERSION_9
}

@Suppress("unused")
@CName("JNI_OnUnload")
fun onUnload(vm: CValuesRef<JavaVMVar>?,
             reserved: CValuesRef<*>?) {
  println("JNI library is unloaded!")
}

