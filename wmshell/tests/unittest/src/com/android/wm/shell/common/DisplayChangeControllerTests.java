

package com.android.wm.shell.common;

import static com.android.dx.mockito.inline.extended.ExtendedMockito.spy;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import android.view.IWindowManager;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;

import com.android.wm.shell.ShellTestCase;
import com.android.wm.shell.sysui.ShellInit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Tests for the display change controller.
 *
 * Build/Install/Run:
 *  atest WMShellUnitTests:DisplayChangeControllerTests
 */
@SmallTest
@RunWith(AndroidJUnit4.class)
public class DisplayChangeControllerTests extends ShellTestCase {

    private @Mock IWindowManager mWM;
    private @Mock ShellInit mShellInit;
    private @Mock ShellExecutor mMainExecutor;
    private DisplayChangeController mController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mController = spy(new DisplayChangeController(mWM, mShellInit, mMainExecutor));
    }

    @Test
    public void instantiate_addInitCallback() {
        verify(mShellInit, times(1)).addInitCallback(any(), any());
    }
}
