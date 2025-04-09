
package com.android.launcher3.util;

import android.window.OnBackAnimationCallback;

/**
 * Extension of {@link OnBackAnimationCallback} that allows a check to determine
 * if this callback supports handling back or not
 */
public interface BackPressHandler extends OnBackAnimationCallback {
    boolean canHandleBack();
}
