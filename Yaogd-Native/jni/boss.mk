# the set of ndk samples reorganize by yaoguangdong

LOCAL_PATH := $(call my-dir)

############################### secret key build #######################
include $(CLEAR_VARS)
LOCAL_MODULE    := sk
LOCAL_SRC_FILES := signature/sk.c
LOCAL_LDLIBS    := -llog
include $(BUILD_SHARED_LIBRARY)

############################### static lib build #######################

include $(CLEAR_VARS)
LOCAL_MODULE    := statlib
LOCAL_SRC_FILES := native-test/statlib.c
include $(BUILD_STATIC_LIBRARY)

############################### static caller lib build ##################

include $(CLEAR_VARS)
LOCAL_MODULE    := stat
LOCAL_SRC_FILES := native-test/stat.c
LOCAL_LDLIBS    := -llog
LOCAL_STATIC_LIBRARIES := statlib
include $(BUILD_SHARED_LIBRARY)

############################### build native activity #####################

