

package com.android.wm.shell.flicker.pip.tv

import android.app.Instrumentation
import android.content.pm.PackageManager
import android.view.Surface
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import org.junit.Before
import org.junit.runners.Parameterized

abstract class PipTestBase(protected val rotationName: String, protected val rotation: Int) {
    val instrumentation: Instrumentation = InstrumentationRegistry.getInstrumentation()
    val uiDevice = UiDevice.getInstance(instrumentation)
    val packageManager: PackageManager = instrumentation.context.packageManager
    protected val isTelevision: Boolean by lazy {
        packageManager.run {
            hasSystemFeature(PackageManager.FEATURE_LEANBACK) ||
                hasSystemFeature(PackageManager.FEATURE_LEANBACK_ONLY)
        }
    }
    protected val testApp = PipAppHelperTv(instrumentation)

    @Before
    open fun televisionSetUp() {
        /**
         * The super implementation assumes ([org.junit.Assume]) that not running on TV, thus
         * disabling the test on TV. This test, however, *should run on TV*, so we overriding this
         * method and simply leaving it blank.
         */
    }

    companion object {
        @Parameterized.Parameters(name = "{0}")
        @JvmStatic
        fun getParams(): Collection<Array<Any>> {
            val supportedRotations = intArrayOf(Surface.ROTATION_0, Surface.ROTATION_90)
            return supportedRotations.map { arrayOf(Surface.rotationToString(it), it) }
        }
    }
}
