
package com.android.quickstep.util

import android.util.Pair
import com.android.internal.logging.InstanceIdSequence
import com.android.launcher3.logging.InstanceId

object LogUtils {
    @JvmStatic
    fun splitFailureMessage(caller: String, reason: String): String {
        return "($caller) Splitscreen aborted: $reason"
    }

    /**
     * @return a [Pair] of two InstanceIds but with different types, one that can be used by
     *   framework (if needing to pass through an intent or such) and one used in Launcher
     */
    @JvmStatic
    fun getShellShareableInstanceId(): Pair<com.android.internal.logging.InstanceId, InstanceId> {
        val internalInstanceId = InstanceIdSequence(InstanceId.INSTANCE_ID_MAX).newInstanceId()
        val launcherInstanceId = InstanceId(internalInstanceId.id)
        return Pair(internalInstanceId, launcherInstanceId)
    }
}
