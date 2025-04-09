

package com.android.wm.shell.onehanded;

import static com.google.common.truth.Truth.assertThat;

import static org.mockito.Mockito.spy;

import android.testing.AndroidTestingRunner;

import androidx.test.filters.SmallTest;

import com.android.wm.shell.common.ShellExecutor;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@SmallTest
@RunWith(AndroidTestingRunner.class)
public class OneHandedTouchHandlerTest extends OneHandedTestCase {
    boolean mIsEventCallback = false;

    private OneHandedTouchHandler mTouchHandler;
    private OneHandedTimeoutHandler mSpiedTimeoutHandler;
    private OneHandedTouchHandler.OneHandedTouchEventCallback mTouchEventCallback =
            () -> mIsEventCallback = true;
    @Mock
    private ShellExecutor mMockShellMainExecutor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mSpiedTimeoutHandler = spy(new OneHandedTimeoutHandler(mMockShellMainExecutor));
        mTouchHandler = new OneHandedTouchHandler(mSpiedTimeoutHandler, mMockShellMainExecutor);
    }

    @Test
    public void testRegisterTouchEventListener() {
        OneHandedTouchHandler.OneHandedTouchEventCallback callback = () -> {
        };
        mTouchHandler.registerTouchEventListener(callback);

        assertThat(mIsEventCallback).isFalse();
    }

    @Test
    public void testOneHandedDisabled_shouldDisposeInputChannel() {
        mTouchHandler.onOneHandedEnabled(false);

        assertThat(mTouchHandler.mInputMonitor).isNull();
        assertThat(mTouchHandler.mInputEventReceiver).isNull();
    }

    @Ignore("b/167943723, refactor it and fix it")
    @Test
    public void testOneHandedEnabled_monitorInputChannel() {
        mTouchHandler.onOneHandedEnabled(true);

        assertThat(mTouchHandler.mInputMonitor).isNotNull();
        assertThat(mTouchHandler.mInputEventReceiver).isNotNull();
    }
}
