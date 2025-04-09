
package com.android.systemui.unfold.config

interface UnfoldTransitionConfig {
    val isEnabled: Boolean
    val isHingeAngleEnabled: Boolean
    val isHapticsEnabled: Boolean
    val halfFoldedTimeoutMillis: Int
}
