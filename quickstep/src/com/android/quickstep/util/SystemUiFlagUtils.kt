
package com.android.quickstep.util

import com.android.systemui.shared.system.QuickStepContract
import com.android.systemui.shared.system.QuickStepContract.SystemUiStateFlags

/** Util class for holding and checking [SystemUiStateFlags] masks. */
object SystemUiFlagUtils {
    const val KEYGUARD_SYSUI_FLAGS =
        (QuickStepContract.SYSUI_STATE_BOUNCER_SHOWING or
            QuickStepContract.SYSUI_STATE_STATUS_BAR_KEYGUARD_SHOWING or
            QuickStepContract.SYSUI_STATE_DEVICE_DOZING or
            QuickStepContract.SYSUI_STATE_OVERVIEW_DISABLED or
            QuickStepContract.SYSUI_STATE_HOME_DISABLED or
            QuickStepContract.SYSUI_STATE_BACK_DISABLED or
            QuickStepContract.SYSUI_STATE_STATUS_BAR_KEYGUARD_SHOWING_OCCLUDED or
            QuickStepContract.SYSUI_STATE_WAKEFULNESS_MASK)

    // If any of these SysUi flags (via QuickstepContract) is set, the device to be considered
    // locked.
    private const val MASK_ANY_SYSUI_LOCKED =
        (QuickStepContract.SYSUI_STATE_BOUNCER_SHOWING or
            QuickStepContract.SYSUI_STATE_STATUS_BAR_KEYGUARD_SHOWING or
            QuickStepContract.SYSUI_STATE_STATUS_BAR_KEYGUARD_SHOWING_OCCLUDED or
            QuickStepContract.SYSUI_STATE_DEVICE_DREAMING)

    /**
     * Returns true iff the given [SystemUiStateFlags] imply that the device is considered locked.
     */
    @JvmStatic
    fun isLocked(@SystemUiStateFlags flags: Long): Boolean {
        return hasAnyFlag(flags, MASK_ANY_SYSUI_LOCKED) &&
            !hasAnyFlag(flags, QuickStepContract.SYSUI_STATE_STATUS_BAR_KEYGUARD_GOING_AWAY)
    }

    private fun hasAnyFlag(@SystemUiStateFlags flags: Long, flagMask: Long): Boolean {
        return (flags and flagMask) != 0L
    }
}
