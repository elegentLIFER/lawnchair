

package com.android.wm.shell.windowdecor.extension

import android.app.TaskInfo
import android.app.WindowConfiguration.WINDOWING_MODE_FULLSCREEN
import android.view.WindowInsetsController.APPEARANCE_LIGHT_CAPTION_BARS
import android.view.WindowInsetsController.APPEARANCE_TRANSPARENT_CAPTION_BAR_BACKGROUND

val TaskInfo.isTransparentCaptionBarAppearance: Boolean
    get() {
        val appearance = taskDescription?.topOpaqueSystemBarsAppearance ?: 0
        return (appearance and APPEARANCE_TRANSPARENT_CAPTION_BAR_BACKGROUND) != 0
    }

val TaskInfo.isLightCaptionBarAppearance: Boolean
    get() {
        val appearance = taskDescription?.topOpaqueSystemBarsAppearance ?: 0
        return (appearance and APPEARANCE_LIGHT_CAPTION_BARS) != 0
    }

val TaskInfo.isFullscreen: Boolean
    get() = windowingMode == WINDOWING_MODE_FULLSCREEN
