

package com.android.launcher3.util

import android.content.Context
import android.content.res.TypedArray
import android.content.res.XmlResourceParser
import android.util.AttributeSet
import kotlin.IntArray

/**
 * This class is a helper that can be subclassed in tests to provide a way to parse attributes
 * correctly.
 */
open class ResourceHelper(private val context: Context, private val specsFileId: Int) {
    open fun getXml(): XmlResourceParser {
        return context.resources.getXml(specsFileId)
    }

    open fun obtainStyledAttributes(attrs: AttributeSet, styleId: IntArray): TypedArray {
        return context.obtainStyledAttributes(attrs, styleId)
    }
}
