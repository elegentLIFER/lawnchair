
package com.android.systemui.unfold.util

interface CallbackController<T> {
    fun addCallback(listener: T)
    fun removeCallback(listener: T)
}
