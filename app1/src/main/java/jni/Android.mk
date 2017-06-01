LOCAL_PATH		:= $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := genius_blur
LOCAL_SRC_FILES := stackblur.c com_skyun_music_util_blur_StackNative.c load.c
LOCAL_LDLIBS    := -lm -llog -ljnigraphics

include $(BUILD_SHARED_LIBRARY)