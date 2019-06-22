Loading Kotlin/Native binary as JNI library
============

The idea of that project is to experiment with both JVM (11 in my case)
and Kotlin/Native. 

What we do 
 - we run Java application 
 - we build shared library with Kotlin/Native to implement JNI contracts
 - we load Kotlin/Native library into JVM application
 
Hack with pleasure!

License
=======

MIT, see the LICENSE file in the repository


Building and Running
====================

Execute `./gradlew build` task. 
Fix path to the library in Java sources. 
Start Java application

