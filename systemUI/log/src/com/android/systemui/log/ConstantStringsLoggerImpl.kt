package com.android.systemui.log

import com.android.systemui.log.core.LogLevel
import com.google.errorprone.annotations.CompileTimeConstant

class ConstantStringsLoggerImpl(val buffer: LogBuffer, val tag: String) : ConstantStringsLogger {
    override fun v(@CompileTimeConstant msg: String) = buffer.log(tag, LogLevel.VERBOSE, msg)

    override fun d(@CompileTimeConstant msg: String) = buffer.log(tag, LogLevel.DEBUG, msg)

    override fun w(@CompileTimeConstant msg: String) = buffer.log(tag, LogLevel.WARNING, msg)

    override fun e(@CompileTimeConstant msg: String) = buffer.log(tag, LogLevel.ERROR, msg)
}
