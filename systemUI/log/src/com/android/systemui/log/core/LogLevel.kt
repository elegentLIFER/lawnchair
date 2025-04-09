

package com.android.systemui.log.core

import android.util.Log

/** Enum version of @Log.Level */
enum class LogLevel(@Log.Level val nativeLevel: Int, val shortString: String) {
    VERBOSE(Log.VERBOSE, "V"),
    DEBUG(Log.DEBUG, "D"),
    INFO(Log.INFO, "I"),
    WARNING(Log.WARN, "W"),
    ERROR(Log.ERROR, "E"),
    WTF(Log.ASSERT, "WTF")
}
