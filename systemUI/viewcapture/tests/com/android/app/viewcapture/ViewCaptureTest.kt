

package com.android.app.viewcapture

import android.content.Intent
import android.media.permission.SafeCloseable
import android.testing.AndroidTestingRunner
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.android.app.viewcapture.TestActivity.Companion.TEXT_VIEW_COUNT
import com.android.app.viewcapture.data.MotionWindowData
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@SmallTest
@RunWith(AndroidTestingRunner::class)
class ViewCaptureTest {

    private val memorySize = 100
    private val initPoolSize = 15
    private val viewCapture by lazy {
        object :
            ViewCapture(memorySize, initPoolSize, MAIN_EXECUTOR) {}
    }

    private val activityIntent =
        Intent(InstrumentationRegistry.getInstrumentation().context, TestActivity::class.java)

    @get:Rule val activityScenarioRule = ActivityScenarioRule<TestActivity>(activityIntent)

    @Test
    fun testWindowListenerDumpsOneFrameAfterInvalidate() {
        activityScenarioRule.scenario.onActivity { activity ->
            val closeable = startViewCaptureAndInvalidateNTimes(1, activity)
            val rootView = activity.requireViewById<View>(android.R.id.content)
            val data = viewCapture.getDumpTask(rootView).get().get()

            assertEquals(1, data.frameDataList.size)
            verifyTestActivityViewHierarchy(data)
            closeable.close()
        }
    }

    @Test
    fun testWindowListenerDumpsCorrectlyAfterRecyclingStarted() {
        activityScenarioRule.scenario.onActivity { activity ->
            val closeable = startViewCaptureAndInvalidateNTimes(memorySize + 5, activity)
            val rootView = activity.requireViewById<View>(android.R.id.content)
            val data = viewCapture.getDumpTask(rootView).get().get()

            // since ViewCapture MEMORY_SIZE is [viewCaptureMemorySize], only
            // [viewCaptureMemorySize] frames are exported, although the view is invalidated
            // [viewCaptureMemorySize + 5] times
            assertEquals(memorySize, data.frameDataList.size)
            verifyTestActivityViewHierarchy(data)
            closeable.close()
        }
    }

    private fun startViewCaptureAndInvalidateNTimes(n: Int, activity: TestActivity): SafeCloseable {
        val rootView: View = activity.requireViewById(android.R.id.content)
        val closeable: SafeCloseable = viewCapture.startCapture(rootView, "rootViewId")
        dispatchOnDraw(rootView, times = n)
        return closeable
    }

    private fun dispatchOnDraw(view: View, times: Int) {
        if (times > 0) {
            view.viewTreeObserver.dispatchOnDraw()
            dispatchOnDraw(view, times - 1)
        }
    }

    private fun verifyTestActivityViewHierarchy(exportedData: MotionWindowData) {
        for (frame in exportedData.frameDataList) {
            val testActivityRoot =
                frame.node // FrameLayout (android.R.id.content)
                    .childrenList
                    .first() // LinearLayout (set by setContentView())
            assertEquals(TEXT_VIEW_COUNT, testActivityRoot.childrenList.size)
            assertEquals(
                LinearLayout::class.qualifiedName,
                exportedData.getClassname(testActivityRoot.classnameIndex)
            )
            assertEquals(
                TextView::class.qualifiedName,
                exportedData.getClassname(testActivityRoot.childrenList.first().classnameIndex)
            )
        }
    }
}
