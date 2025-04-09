

package com.android.app.viewcapture

import android.Manifest
import android.content.Context
import android.content.Intent
import android.media.permission.SafeCloseable
import android.provider.Settings
import android.testing.AndroidTestingRunner
import android.view.Choreographer
import android.view.View
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import com.android.app.viewcapture.SettingsAwareViewCapture.Companion.VIEW_CAPTURE_ENABLED
import com.android.app.viewcapture.ViewCapture.MAIN_EXECUTOR
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@SmallTest
@RunWith(AndroidTestingRunner::class)
class SettingsAwareViewCaptureTest {
    private val context: Context = InstrumentationRegistry.getInstrumentation().context
    private val activityIntent = Intent(context, TestActivity::class.java)

    @get:Rule val activityScenarioRule = ActivityScenarioRule<TestActivity>(activityIntent)
    @get:Rule val grantPermissionRule =
        GrantPermissionRule.grant(Manifest.permission.WRITE_SECURE_SETTINGS)

    @Test
    fun do_not_capture_view_hierarchies_if_setting_is_disabled() {
        Settings.Global.putInt(context.contentResolver, VIEW_CAPTURE_ENABLED, 0)

        activityScenarioRule.scenario.onActivity { activity ->
            val viewCapture: ViewCapture = SettingsAwareViewCapture(context, MAIN_EXECUTOR)
            val rootView: View = activity.requireViewById(android.R.id.content)

            val closeable: SafeCloseable = viewCapture.startCapture(rootView, "rootViewId")
            Choreographer.getInstance().postFrameCallback {
                rootView.viewTreeObserver.dispatchOnDraw()

                assertEquals(
                    0,
                    viewCapture
                        .getDumpTask(activity.requireViewById(android.R.id.content))
                        .get()
                        .get()
                        .frameDataList
                        .size
                )
                closeable.close()
            }
        }
    }

    @Test
    fun capture_view_hierarchies_if_setting_is_enabled() {
        Settings.Global.putInt(context.contentResolver, VIEW_CAPTURE_ENABLED, 1)

        activityScenarioRule.scenario.onActivity { activity ->
            val viewCapture: ViewCapture = SettingsAwareViewCapture(context, MAIN_EXECUTOR)
            val rootView: View = activity.requireViewById(android.R.id.content)

            val closeable: SafeCloseable = viewCapture.startCapture(rootView, "rootViewId")
            Choreographer.getInstance().postFrameCallback {
                rootView.viewTreeObserver.dispatchOnDraw()

                assertEquals(
                    1,
                    viewCapture
                        .getDumpTask(activity.requireViewById(android.R.id.content))
                        .get()
                        .get()
                        .frameDataList
                        .size
                )

                closeable.close()
            }
        }
    }

    @Test
    fun getInstance_calledTwiceInARow_returnsSameObject() {
        assertEquals(
            SettingsAwareViewCapture.getInstance(context).hashCode(),
            SettingsAwareViewCapture.getInstance(context).hashCode()
        )
    }
}
