

package com.android.launcher3.util

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.android.launcher3.R
import kotlin.IntArray

class TestResourceHelper(private val context: Context, specsFileId: Int) :
    ResourceHelper(context, specsFileId) {

    val responsiveStyleables = listOf(
            R.styleable.SizeSpec,
            R.styleable.WorkspaceSpec,
            R.styleable.FolderSpec,
            R.styleable.AllAppsSpec,
            R.styleable.ResponsiveSpecGroup
    )

    override fun obtainStyledAttributes(attrs: AttributeSet, styleId: IntArray): TypedArray {
        val clone =
                if (responsiveStyleables.any { styleId.contentEquals(it) }) {
                    convertStyleId(styleId)
                } else {
                    styleId.clone()
                }

        return context.obtainStyledAttributes(attrs, clone)
    }

    private fun convertStyleId(styleableArr: IntArray): IntArray {
        val targetContextRes = getInstrumentation().targetContext.resources
        val context = getInstrumentation().context
        return styleableArr
            .map { attrId -> targetContextRes.getResourceName(attrId).split(":").last() }
            .map { attrName ->
                // Get required attr from context instead of targetContext
                context.resources.getIdentifier(attrName, null, context.packageName)
            }
            .toIntArray()
    }
}
