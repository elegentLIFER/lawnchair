

package com.android.systemui.log.core

import android.util.Log
import com.android.systemui.log.LogMessageImpl

/**
 * A simple implementation of [MessageBuffer] that forwards messages to [android.util.Log]
 * immediately. This defeats the intention behind [LogBuffer] and should only be used when
 * [LogBuffer]s are unavailable in a certain context.
 */
class LogcatOnlyMessageBuffer(
    val targetLogLevel: LogLevel,
) : MessageBuffer {
    private val singleMessage = LogMessageImpl.Factory.create()
    private var isObtained: Boolean = false

    @Synchronized
    override fun obtain(
        tag: String,
        level: LogLevel,
        messagePrinter: MessagePrinter,
        exception: Throwable?,
    ): LogMessage {
        if (isObtained) {
            throw UnsupportedOperationException(
                "Message has already been obtained. Call order is incorrect."
            )
        }

        singleMessage.reset(tag, level, System.currentTimeMillis(), messagePrinter, exception)
        isObtained = true
        return singleMessage
    }

    @Synchronized
    override fun commit(message: LogMessage) {
        if (singleMessage != message) {
            throw IllegalArgumentException("Message argument is not the expected message.")
        }
        if (!isObtained) {
            throw UnsupportedOperationException(
                "Message has not been obtained. Call order is incorrect."
            )
        }

        if (message.level >= targetLogLevel) {
            val strMessage = message.messagePrinter(message)
            when (message.level) {
                LogLevel.VERBOSE -> Log.v(message.tag, strMessage, message.exception)
                LogLevel.DEBUG -> Log.d(message.tag, strMessage, message.exception)
                LogLevel.INFO -> Log.i(message.tag, strMessage, message.exception)
                LogLevel.WARNING -> Log.w(message.tag, strMessage, message.exception)
                LogLevel.ERROR -> Log.e(message.tag, strMessage, message.exception)
                LogLevel.WTF -> Log.wtf(message.tag, strMessage, message.exception)
            }
        }

        isObtained = false
    }
}
