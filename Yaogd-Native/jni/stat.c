#include <string.h>
#include <jni.h>

#if defined(__arm__)
  #if defined(__ARM_ARCH_7A__)
    #if defined(__ARM_NEON__)
      #if defined(__ARM_PCS_VFP)
        #define ABI "armeabi-v7a/NEON (hard-float)"
      #else
        #define ABI "armeabi-v7a/NEON"
      #endif
    #else
      #if defined(__ARM_PCS_VFP)
        #define ABI "armeabi-v7a (hard-float)"
      #else
        #define ABI "armeabi-v7a"
      #endif
    #endif
  #else
   #define ABI "armeabi"
  #endif
#elif defined(__i386__)
   #define ABI "x86"
#elif defined(__x86_64__)
   #define ABI "x86_64"
#elif defined(__mips64)  /* mips64el-* toolchain defines __mips__ too */
   #define ABI "mips64"
#elif defined(__mips__)
   #define ABI "mips"
#elif defined(__aarch64__)
   #define ABI "arm64-v8a"
#else
   #define ABI "unknown"
#endif

jstring Java_com_yaogd_nativ_LoadNative_get( JNIEnv* env, jobject thiz)
{
    return (*env)->NewStringUTF(env, "static call !  Compiled with ABI " ABI ".");
}

jstring Java_com_yaogd_nativ_LoadNative_getSecretKey( JNIEnv* env, jobject thiz)
{
	jclass cls = (*env)->FindClass(env, "android/content/ContextWrapper");
	//this.getPackageManager();
	jmethodID mid = (*env)->GetMethodID(env, cls, "getPackageManager",
			"()Landroid/content/pm/PackageManager;");
	if (mid == NULL) {
		return (*env)->NewStringUTF(env, "-1");
	}

	jobject pm = (*env)->CallObjectMethod(env, thiz, mid);
	if (pm == NULL) {
		return (*env)->NewStringUTF(env, "-2");
	}

	//this.getPackageName();
	mid = (*env)->GetMethodID(env, cls, "getPackageName", "()Ljava/lang/String;");
	if (mid == NULL) {
		return (*env)->NewStringUTF(env, "-3");
	}

	jstring packageName = (jstring)(*env)->CallObjectMethod(env, thiz, mid);

	// packageManager->getPackageInfo(packageName, GET_SIGNATURES);
	cls = (*env)->GetObjectClass(env, pm);
	mid  = (*env)->GetMethodID(env, cls, "getPackageInfo", "(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;");
	jobject packageInfo = (*env)->CallObjectMethod(env, pm, mid, packageName, 0x40); //GET_SIGNATURES = 64;
	cls = (*env)->GetObjectClass(env, packageInfo);
	jfieldID fid = (*env)->GetFieldID(env, cls, "signatures", "[Landroid/content/pm/Signature;");
	jobjectArray signatures = (jobjectArray)(*env)->GetObjectField(env, packageInfo, fid);
	jobject sig = (*env)->GetObjectArrayElement(env, signatures, 0);

	cls = (*env)->GetObjectClass(env, sig);
	mid = (*env)->GetMethodID(env, cls, "hashCode", "()I");
	int sig_value = (int)(*env)->CallIntMethod(env, sig, mid);

	if(sig_value == -1352853962){
		return (*env)->NewStringUTF(env, "305CF3900351DDFFF9");
	}else if(sig_value == 1721194776){
		return (*env)->NewStringUTF(env, "305CF3900351DDFFF9");
	}else{
		return (*env)->NewStringUTF(env, "0");
	}

}
