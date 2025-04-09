
package com.android.systemui.unfold.config

import android.content.res.Resources
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResourceUnfoldTransitionConfig @Inject constructor() : UnfoldTransitionConfig {

    private fun getBooleanResource(resourceName: String): Boolean {
        val id = Resources.getSystem().getIdentifier(resourceName, "bool", "android")
        return if (id != 0) {
            Resources.getSystem().getBoolean(id)
        } else {
            false
        }
    }

    private fun getIntResource(resourceName: String): Int {
        val id = Resources.getSystem().getIdentifier(resourceName, "integer", "android")
        return if (id != 0) {
            Resources.getSystem().getInteger(id)
        } else {
            0
        }
    }

    override val isEnabled: Boolean by lazy {
        getBooleanResource("config_unfoldTransitionEnabled")
    }

    override val isHingeAngleEnabled: Boolean by lazy {
        getBooleanResource("config_unfoldTransitionHingeAngle")
    }

    override val isHapticsEnabled: Boolean by lazy {
        getBooleanResource("config_unfoldTransitionHapticsEnabled")
    }

    override val halfFoldedTimeoutMillis: Int by lazy {
        getIntResource("config_unfoldTransitionHalfFoldedTimeout")
    }
}
