

package com.android.systemui.log

import com.android.systemui.log.core.LogLevel

/** Keeps track of which [LogBuffer] messages should also appear in logcat. */
interface LogcatEchoTracker {
    /** Whether [bufferName] should echo messages of [level] or higher to logcat. */
    fun isBufferLoggable(bufferName: String, level: LogLevel): Boolean

    /** Whether [tagName] should echo messages of [level] or higher to logcat. */
    fun isTagLoggable(tagName: String, level: LogLevel): Boolean
}
