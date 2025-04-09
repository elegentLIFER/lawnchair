

package com.android.launcher3;

import static android.view.MotionEvent.CLASSIFICATION_TWO_FINGER_SWIPE;

import static com.android.launcher3.config.FeatureFlags.ENABLE_TRACKPAD_GESTURE;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.MotionEvent;

/** Handles motion events from trackpad. */
public class MotionEventsUtils {

    /** {@link MotionEvent#CLASSIFICATION_MULTI_FINGER_SWIPE} is hidden. */
    public static final int CLASSIFICATION_MULTI_FINGER_SWIPE = 4;

    /** {@link MotionEvent#AXIS_GESTURE_SWIPE_FINGER_COUNT} is hidden. */
    private static final int AXIS_GESTURE_SWIPE_FINGER_COUNT = 53;

    @TargetApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    public static boolean isTrackpadScroll(MotionEvent event) {
        return ENABLE_TRACKPAD_GESTURE.get()
                && event.getClassification() == CLASSIFICATION_TWO_FINGER_SWIPE;
    }

    @TargetApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    public static boolean isTrackpadMultiFingerSwipe(MotionEvent event) {
        return Utilities.ATLEAST_U && ENABLE_TRACKPAD_GESTURE.get ( )
                && event.getClassification ( ) == CLASSIFICATION_MULTI_FINGER_SWIPE;
    }

    public static boolean isTrackpadThreeFingerSwipe(MotionEvent event) {
        return isTrackpadMultiFingerSwipe(event) && event.getAxisValue(
                AXIS_GESTURE_SWIPE_FINGER_COUNT) == 3;
    }

    public static boolean isTrackpadFourFingerSwipe(MotionEvent event) {
        return isTrackpadMultiFingerSwipe(event) && event.getAxisValue(
                AXIS_GESTURE_SWIPE_FINGER_COUNT) == 4;
    }

    public static boolean isTrackpadMotionEvent(MotionEvent event) {
         return Utilities.ATLEAST_U && (isTrackpadScroll(event) || isTrackpadMultiFingerSwipe(event));
    }
}
