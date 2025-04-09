

package com.android.systemui.animation.back

import android.util.DisplayMetrics

/**
 * SysUI transitions - Dismiss app (ST1) Return to launching surface or place of origin
 * https://carbon.googleplex.com/predictive-back-for-apps/pages/st-1-dismiss-app
 */
fun BackAnimationSpec.Companion.dismissAppForSysUi(
    displayMetricsProvider: () -> DisplayMetrics,
): BackAnimationSpec =
    BackAnimationSpec.createFloatingSurfaceAnimationSpec(
        displayMetricsProvider = displayMetricsProvider,
        maxMarginXdp = 8f,
        maxMarginYdp = 8f,
        minScale = 0.8f,
    )

/**
 * SysUI transitions - Cross task (ST2) Return to previous task/app, keeping the current one open
 * https://carbon.googleplex.com/predictive-back-for-apps/pages/st-2-cross-task
 */
fun BackAnimationSpec.Companion.crossTaskForSysUi(
    displayMetricsProvider: () -> DisplayMetrics,
): BackAnimationSpec =
    BackAnimationSpec.createFloatingSurfaceAnimationSpec(
        displayMetricsProvider = displayMetricsProvider,
        maxMarginXdp = 8f,
        maxMarginYdp = 8f,
        minScale = 0.8f,
    )

/**
 * SysUI transitions - Inner area dismiss (ST3) Dismiss non-detachable surface
 * https://carbon.googleplex.com/predictive-back-for-apps/pages/st-3-inner-area-dismiss
 */
fun BackAnimationSpec.Companion.innerAreaDismissForSysUi(
    displayMetricsProvider: () -> DisplayMetrics,
): BackAnimationSpec =
    BackAnimationSpec.createFloatingSurfaceAnimationSpec(
        displayMetricsProvider = displayMetricsProvider,
        maxMarginXdp = 0f,
        maxMarginYdp = 0f,
        minScale = 0.9f,
    )

/**
 * SysUI transitions - Floating system surfaces (ST4)
 * https://carbon.googleplex.com/predictive-back-for-apps/pages/st-4-floating-system-surfaces
 */
fun BackAnimationSpec.Companion.floatingSystemSurfacesForSysUi(
    displayMetricsProvider: () -> DisplayMetrics,
): BackAnimationSpec =
    BackAnimationSpec.createFloatingSurfaceAnimationSpec(
        displayMetricsProvider = displayMetricsProvider,
        maxMarginXdp = 8f,
        maxMarginYdp = 8f,
        minScale = 0.9f,
    )

/**
 * SysUI transitions - Bottomsheet (AT3)
 * https://carbon.googleplex.com/predictive-back-for-apps/pages/at-3-bottom-sheets
 */
fun BackAnimationSpec.Companion.bottomSheetForSysUi(
    displayMetricsProvider: () -> DisplayMetrics,
): BackAnimationSpec = BackAnimationSpec.createBottomsheetAnimationSpec(displayMetricsProvider)
