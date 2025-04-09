

package com.android.quickstep.util

import com.android.launcher3.util.SplitConfigurationOptions
import com.android.wm.shell.util.SplitBounds

class SplitScreenUtils {
    companion object {
        // TODO(b/254378592): Remove these methods when the two classes are reunited
        /** Converts the shell version of SplitBounds to the launcher version */
        @JvmStatic
        fun convertShellSplitBoundsToLauncher(
            shellSplitBounds: SplitBounds?
        ): SplitConfigurationOptions.SplitBounds? {
            return if (shellSplitBounds == null) {
                null
            } else {
                SplitConfigurationOptions.SplitBounds(
                    shellSplitBounds.leftTopBounds, shellSplitBounds.rightBottomBounds,
                    shellSplitBounds.leftTopTaskId, shellSplitBounds.rightBottomTaskId,
                    shellSplitBounds.snapPosition
                )
            }
        }

        /** Converts the launcher version of SplitBounds to the shell version */
        @JvmStatic
        fun convertLauncherSplitBoundsToShell(
            launcherSplitBounds: SplitConfigurationOptions.SplitBounds?
        ): SplitBounds? {
            return if (launcherSplitBounds == null) {
                null
            } else {
                SplitBounds(
                    launcherSplitBounds.leftTopBounds,
                    launcherSplitBounds.rightBottomBounds,
                    launcherSplitBounds.leftTopTaskId,
                    launcherSplitBounds.rightBottomTaskId,
                    launcherSplitBounds.snapPosition
                )
            }
        }
    }
}
