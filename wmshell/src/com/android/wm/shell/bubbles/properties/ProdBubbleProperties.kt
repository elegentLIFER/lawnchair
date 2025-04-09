

package com.android.wm.shell.bubbles.properties

import android.os.SystemProperties
import com.android.wm.shell.Flags

/** Provides bubble properties in production. */
object ProdBubbleProperties : BubbleProperties {

    private var _isBubbleBarEnabled = Flags.enableBubbleBar() ||
            SystemProperties.getBoolean("persist.wm.debug.bubble_bar", false)

    override val isBubbleBarEnabled
        get() = _isBubbleBarEnabled

    override fun refresh() {
        _isBubbleBarEnabled = Flags.enableBubbleBar() ||
                SystemProperties.getBoolean("persist.wm.debug.bubble_bar", false)
    }
}
