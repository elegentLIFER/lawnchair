

package com.android.wm.shell;

import static android.view.Display.DEFAULT_DISPLAY;
import static org.junit.Assume.assumeTrue;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.display.DisplayManager;
import android.testing.TestableContext;

import androidx.test.platform.app.InstrumentationRegistry;

import com.android.internal.protolog.common.ProtoLog;

import org.junit.After;
import org.junit.Before;
import org.mockito.MockitoAnnotations;

/**
 * Base class that does shell test case setup.
 */
public abstract class ShellTestCase {

    protected TestableContext mContext;
    private PackageManager mPm;

    @Before
    public void shellSetup() {
        // Disable protolog tool when running the tests from studio
        ProtoLog.REQUIRE_PROTOLOGTOOL = false;

        MockitoAnnotations.initMocks(this);
        final Context context =
                InstrumentationRegistry.getInstrumentation().getTargetContext();
        final DisplayManager dm = context.getSystemService(DisplayManager.class);
        mPm = context.getPackageManager();
        mContext = new TestableContext(
                context.createDisplayContext(dm.getDisplay(DEFAULT_DISPLAY)));

        InstrumentationRegistry
                .getInstrumentation()
                .getUiAutomation()
                .adoptShellPermissionIdentity();
    }

    @After
    public void shellTearDown() {
        InstrumentationRegistry
                .getInstrumentation()
                .getUiAutomation()
                .dropShellPermissionIdentity();
    }

    protected Context getContext() {
        return mContext;
    }

    /**
     * Makes an assumption that the test device is a TV device, used to guard tests that should
     * only be run on TVs.
     */
    protected void assumeTelevision() {
        assumeTrue(isTelevision());
    }

    /**
     * Returns whether this test device is a TV device.
     */
    protected boolean isTelevision() {
        return mPm.hasSystemFeature(PackageManager.FEATURE_LEANBACK)
                || mPm.hasSystemFeature(PackageManager.FEATURE_LEANBACK_ONLY);
    }
}
