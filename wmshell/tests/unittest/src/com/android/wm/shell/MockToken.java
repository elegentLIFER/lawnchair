

package com.android.wm.shell;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.os.IBinder;
import android.window.WindowContainerToken;

/**
 * {@link WindowContainerToken} wrapper that supports a mock binder
 */
public class MockToken {
    private final WindowContainerToken mToken;

    public MockToken() {
        mToken = mock(WindowContainerToken.class);
        IBinder binder = mock(IBinder.class);
        when(mToken.asBinder()).thenReturn(binder);
    }

    public WindowContainerToken token() {
        return mToken;
    }
}
