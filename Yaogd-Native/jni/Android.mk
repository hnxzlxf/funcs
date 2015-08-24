# Copyright (C) 2009 The Android Open Source Project

LOCAL_PATH := $(call my-dir)

#secret key build
include $(CLEAR_VARS)
LOCAL_MODULE    := sk
LOCAL_SRC_FILES := sk.c
include $(BUILD_SHARED_LIBRARY)

#static call build
include $(CLEAR_VARS)
LOCAL_MODULE    := stat
LOCAL_SRC_FILES := stat.c
include $(BUILD_SHARED_LIBRARY)

