#include <jni.h>
#include <string>
#include "myDrink.h"

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_luke_alcontrolv2_UserState_JSetUser(
        JNIEnv *env, jobject, jint hasRun, jint genderDat, jdouble weightDat) {
    myDrink user1(hasRun);
    user1.setUser(genderDat,weightDat);
    std::string hello = user1.setUser(genderDat,weightDat);
    user1.theWriter();
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_luke_alcontrolv2_UserState_JAddDrink(
        JNIEnv *env, jobject, jint hasRun) {
    myDrink user1(hasRun);
    //user1.addDrink();
    std::string hello = user1.addDrink();
    user1.theWriter();
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_luke_alcontrolv2_UserState_JCalcMyBAC(
        JNIEnv *env, jobject, jint hasRun) {
    myDrink user1(hasRun);
    std::string hello = user1.CalcMyBAC();
    user1.theWriter();
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_luke_alcontrolv2_UserState_JSetDrink(
        JNIEnv *env, jobject, jint hasRun, jdouble MyABV, jdouble MyVol) {
    myDrink user1(hasRun);
    std::string hello = user1.setDrink(MyABV, MyVol);
    user1.theWriter();
    return env->NewStringUTF(hello.c_str());
}
/*
extern "C" JNIEXPORT jstring JNICALL
Java_com_example_luke_alcontrolv2_UserState_JSetCustomDrink(
        JNIEnv *env, jobject) {
    myDrink user1(hasRun);
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
 */

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_luke_alcontrolv2_UserState_JNewSession(
        JNIEnv *env, jobject, jint hasRun) {
myDrink user1(hasRun);
user1.newSession();
user1.theWriter();
std::string hello = "hello";
return env->NewStringUTF(hello.c_str());
}