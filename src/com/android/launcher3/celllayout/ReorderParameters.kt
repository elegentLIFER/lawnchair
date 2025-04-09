

package com.android.launcher3.celllayout

import android.view.View

class ReorderParameters(
    val pixelX: Int,
    val pixelY: Int,
    val spanX: Int,
    val spanY: Int,
    val minSpanX: Int,
    val minSpanY: Int,
    val dragView: View?,
    val solution: ItemConfiguration
) {}
