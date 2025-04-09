

package com.android.systemui.log.core

/**
 * [MessageBuffer] is an interface that represents a buffer of log messages, and provides methods to
 * [obtain] a log message and [commit] it to the buffer.
 */
interface MessageBuffer {
    /**
     * Obtains the next [LogMessage] from the buffer.
     *
     * After calling [obtain], the caller must store any relevant data on the message and then call
     * [commit].
     */
    fun obtain(
        tag: String,
        level: LogLevel,
        messagePrinter: MessagePrinter,
        exception: Throwable? = null,
    ): LogMessage

    /**
     * After acquiring a log message via [obtain], call this method to signal to the buffer that
     * data fields have been filled.
     */
    fun commit(message: LogMessage)
}
