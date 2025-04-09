

package com.android.wm.shell.onehanded;

import static android.view.Display.DEFAULT_DISPLAY;

import static com.android.wm.shell.onehanded.OneHandedController.SUPPORT_ONE_HANDED_MODE;

import static org.junit.Assume.assumeTrue;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.SystemProperties;
import android.testing.TestableContext;
import android.view.WindowManager;

import androidx.test.platform.app.InstrumentationRegistry;

import com.android.wm.shell.ShellTestCase;

import org.junit.Before;
import org.junit.Rule;
import org.mockito.Answers;
import org.mockito.Mock;

/**
 * Base class that does One Handed specific setup.
 */
public abstract class OneHandedTestCase extends ShellTestCase {
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    protected Context mContext;

    @Rule
    public TestableContext mTestContext = new TestableContext(
            InstrumentationRegistry.getInstrumentation().getTargetContext(), null);

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    protected WindowManager mWindowManager;

    @Before
    public void setUpContext() {
        assumeTrue(SystemProperties.getBoolean(SUPPORT_ONE_HANDED_MODE, false));

        final DisplayManager dm = getTestContext().getSystemService(DisplayManager.class);
        mContext = getTestContext().createDisplayContext(dm.getDisplay(DEFAULT_DISPLAY));
    }

    @Before
    public void setUpWindowManager() {
        assumeTrue(SystemProperties.getBoolean(SUPPORT_ONE_HANDED_MODE, false));
        mWindowManager = getTestContext().getSystemService(WindowManager.class);
    }

    /** return testable context */
    protected TestableContext getTestContext() {
        return mTestContext;
    }

    /** return display context */
    protected Context getContext() {
        return mContext;
    }
}
