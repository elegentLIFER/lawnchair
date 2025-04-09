

package com.android.app.viewcapture

import android.app.Activity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView

/**
 * Activity with the content set to a [LinearLayout] with [TextView] children.
 */
class TestActivity : Activity() {

    companion object {
        const val TEXT_VIEW_COUNT = 1000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(createContentView())
    }

    private fun createContentView(): LinearLayout {
        val root = LinearLayout(this)
        for (i in 0 until TEXT_VIEW_COUNT) {
            root.addView(TextView(this))
        }
        return root
    }
}
