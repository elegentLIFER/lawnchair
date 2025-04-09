

package com.android.systemui.shared.system;

import static android.view.CrossWindowBlurListeners.CROSS_WINDOW_BLUR_SUPPORTED;

import android.app.ActivityManager;
import android.os.SystemProperties;

import app.lawnchair.compat.LawnchairQuickstepCompat;

public abstract class BlurUtils {

    /**
     * If this device can render blurs.
     *
     * @return {@code true} when supported.
     */
    public static boolean supportsBlursOnWindows() {
        return LawnchairQuickstepCompat.ATLEAST_R && CROSS_WINDOW_BLUR_SUPPORTED && ActivityManager.isHighEndGfx()
                && !SystemProperties.getBoolean("persist.sysui.disableBlur", false);
    }
}
