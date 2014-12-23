#include <jni.h>

extern "C" jstring
Java_com_example_morrita_hellondk_MainActivity_hello(
    JNIEnv* env, jobject thiz) {
  return env->NewStringUTF("Hello from JNI!");
}
