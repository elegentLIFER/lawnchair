
package com.android.launcher3.util;

import android.view.InputDevice;
import android.view.MotionEvent;

import androidx.annotation.NonNull;

/** Util class for touch event. */
public final class TouchUtil {

    private TouchUtil() {}

    /**
     * Detect ACTION_DOWN or ACTION_MOVE from mouse right button. Note that we cannot detect
     * ACTION_UP from mouse's right button because, in that case,
     * {@link MotionEvent#getButtonState()} returns 0 for any mouse button (right, middle, right).
     */
    public static boolean isMouseRightClickDownOrMove(@NonNull MotionEvent event) {
        return event.isFromSource(InputDevice.SOURCE_MOUSE)
                && ((event.getButtonState() & MotionEvent.BUTTON_SECONDARY) != 0);
    }
}
