

package com.android.systemui.util

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import android.util.TypedValue

/** Convert [this] number of dps to device pixels. */
fun Number.dpToPx(context: Context): Float = dpToPx(resources = context.resources)

/** Convert [this] number of dps to device pixels. */
fun Number.dpToPx(resources: Resources): Float = dpToPx(displayMetrics = resources.displayMetrics)

/** Convert [this] number of dps to device pixels. */
fun Number.dpToPx(displayMetrics: DisplayMetrics): Float =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, toFloat(), displayMetrics)
