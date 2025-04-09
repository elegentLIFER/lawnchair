

@file:JvmName("WaitUtils")

package com.android.wm.shell.flicker.utils

import android.os.SystemClock

private const val DEFAULT_TIMEOUT = 10000L
private const val DEFAULT_POLL_INTERVAL = 1000L

fun wait(condition: () -> Boolean): Boolean {
    val (success, _) = waitForResult(extractor = condition, validator = { it })
    return success
}

fun <R> waitForResult(
    timeout: Long = DEFAULT_TIMEOUT,
    interval: Long = DEFAULT_POLL_INTERVAL,
    extractor: () -> R,
    validator: (R) -> Boolean = { it != null }
): Pair<Boolean, R?> {
    val startTime = SystemClock.uptimeMillis()
    do {
        val result = extractor()
        if (validator(result)) {
            return (true to result)
        }
        SystemClock.sleep(interval)
    } while (SystemClock.uptimeMillis() - startTime < timeout)

    return (false to null)
}
