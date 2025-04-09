
package com.android.quickstep.util

import android.view.Surface
import com.android.launcher3.util.DisplayController.Info
import com.android.launcher3.util.NavigationMode
import com.android.launcher3.util.NavigationMode.NO_BUTTON

/** Utility class to check nav bar position. */
data class NavBarPosition(
    val isTablet: Boolean,
    val displayRotation: Int,
    val mode: NavigationMode
) {
    constructor(
        mode: NavigationMode,
        info: Info
    ) : this(info.isTablet(info.realBounds), info.rotation, mode)

    val isRightEdge: Boolean
        get() = mode != NO_BUTTON && displayRotation == Surface.ROTATION_90 && !isTablet
    val isLeftEdge: Boolean
        get() = mode != NO_BUTTON && displayRotation == Surface.ROTATION_270 && !isTablet

    val rotation: Float
        get() = if (isLeftEdge) 90f else if (isRightEdge) -90f else 0f
}
