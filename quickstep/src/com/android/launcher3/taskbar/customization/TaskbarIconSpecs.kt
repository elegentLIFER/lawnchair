

package com.android.launcher3.taskbar.customization

/** Taskbar Icon Specs */
object TaskbarIconSpecs {

    val iconSize40dp = TaskbarIconSize(40)
    val iconSize44dp = TaskbarIconSize(44)
    val iconSize48dp = TaskbarIconSize(48)
    val iconSize52dp = TaskbarIconSize(52)

    val transientTaskbarIconSizes = arrayOf(iconSize44dp, iconSize48dp, iconSize52dp)

    val defaultPersistentIconSize = iconSize40dp
    val defaultTransientIconSize = iconSize44dp

    // defined as row, columns
    val transientTaskbarIconSizeByGridSize =
        mapOf(
            Pair(6, 5) to iconSize52dp,
            Pair(4, 5) to iconSize48dp,
            Pair(5, 4) to iconSize48dp,
            Pair(4, 4) to iconSize48dp,
            Pair(5, 6) to iconSize44dp,
        )
}
