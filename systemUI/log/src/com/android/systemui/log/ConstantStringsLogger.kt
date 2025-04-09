

package com.android.systemui.log

import com.google.errorprone.annotations.CompileTimeConstant

/**
 * Handy for adding basic logging with CompileTimeConstant strings - so logging with no variables.
 * Most likely you want to delegate it to [ConstantStringsLoggerImpl].
 */
interface ConstantStringsLogger {
    fun v(@CompileTimeConstant msg: String)

    fun d(@CompileTimeConstant msg: String)

    fun w(@CompileTimeConstant msg: String)

    fun e(@CompileTimeConstant msg: String)
}
