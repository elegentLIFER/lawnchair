
package com.android.systemui.unfold.util

import java.lang.ref.WeakReference
import javax.inject.Inject
import javax.inject.Singleton

interface UnfoldKeyguardVisibilityProvider {
    /**
     * True when the keyguard is visible.
     *
     * Might be [null] when it is not known.
     */
    val isKeyguardVisible: Boolean?
}

/** Used to notify keyguard visibility. */
interface UnfoldKeyguardVisibilityManager {
    /** Sets the delegate. [delegate] should return true when the keyguard is visible. */
    fun setKeyguardVisibleDelegate(delegate: () -> Boolean)
}

/**
 * Keeps a [WeakReference] for the keyguard visibility provider.
 *
 * It is a weak reference because this is in the global scope, while the delegate might be set from
 * another subcomponent (that might have shorter lifespan).
 */
@Singleton
class UnfoldKeyguardVisibilityManagerImpl @Inject constructor() :
    UnfoldKeyguardVisibilityProvider, UnfoldKeyguardVisibilityManager {

    private var delegatedProvider: WeakReference<() -> Boolean?>? = null

    override fun setKeyguardVisibleDelegate(delegate: () -> Boolean) {
        delegatedProvider = WeakReference(delegate)
    }

    override val isKeyguardVisible: Boolean?
        get() = delegatedProvider?.get()?.invoke()
}
