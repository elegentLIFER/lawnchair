
package com.android.systemui.unfold.util

interface CurrentActivityTypeProvider {
    val isHomeActivity: Boolean?

    /** Starts listening for task updates. */
    fun init() {}
    /** Stop listening for task updates. */
    fun uninit() {}
}

class EmptyCurrentActivityTypeProvider(override val isHomeActivity: Boolean? = null) :
    CurrentActivityTypeProvider
