

package com.android.launcher3.logging

import androidx.annotation.MainThread

/** Interface to log launcher startup latency metrics. */
interface StartupLatencyLogger {

    @MainThread fun log(): StartupLatencyLogger = this

    @MainThread fun logWorkspaceLoadStartTime(): StartupLatencyLogger = this

    /**
     * Log size of workspace. Larger number of workspace items (icons, folders, widgets) means
     * longer latency to initialize workspace.
     */
    @MainThread fun logCardinality(cardinality: Int): StartupLatencyLogger = this

    @MainThread
    fun logStart(event: StatsLogManager.LauncherLatencyEvent): StartupLatencyLogger = this

    @MainThread
    fun logStart(
        event: StatsLogManager.LauncherLatencyEvent,
        startTimeMs: Long
    ): StartupLatencyLogger = this

    @MainThread fun logEnd(event: StatsLogManager.LauncherLatencyEvent): StartupLatencyLogger = this

    @MainThread
    fun logEnd(event: StatsLogManager.LauncherLatencyEvent, endTimeMs: Long): StartupLatencyLogger =
        this

    @MainThread fun reset()

    companion object {
        val NO_OP: StartupLatencyLogger =
            object : StartupLatencyLogger {
                override fun reset() {}
            }
    }
}
