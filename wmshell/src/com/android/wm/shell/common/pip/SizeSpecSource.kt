

package com.android.wm.shell.common.pip

import android.util.Size
import java.io.PrintWriter

interface SizeSpecSource {
    /** Returns max size allowed for the PIP window  */
    fun getMaxSize(aspectRatio: Float): Size

    /** Returns default size for the PIP window  */
    fun getDefaultSize(aspectRatio: Float): Size

    /** Returns min size allowed for the PIP window  */
    fun getMinSize(aspectRatio: Float): Size

    /** Returns the adjusted size based on current size and target aspect ratio  */
    fun getSizeForAspectRatio(size: Size, aspectRatio: Float): Size

    /** Overrides the minimum pip size requested by the app */
    fun setOverrideMinSize(overrideMinSize: Size?)

    /** Returns the minimum pip size requested by the app */
    fun getOverrideMinSize(): Size?

    /** Returns the minimum edge size of the override minimum size, or 0 if not set.  */
    fun getOverrideMinEdgeSize(): Int {
        val overrideMinSize = getOverrideMinSize() ?: return 0
        return Math.min(overrideMinSize.width, overrideMinSize.height)
    }

    fun onConfigurationChanged() {}

    /** Dumps the internal state of the size spec */
    fun dump(pw: PrintWriter, prefix: String) {}
}
