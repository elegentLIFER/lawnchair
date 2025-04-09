

package com.android.wm.shell;

import static org.mockito.Mockito.RETURNS_SELF;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import android.view.SurfaceControl;

/**
 * Helper class to provide mocks for {@link SurfaceControl.Builder} and
 * {@link SurfaceControl.Transaction} with method chaining support.
 */
public class MockSurfaceControlHelper {
    private MockSurfaceControlHelper() {}

    /**
     * Creates a mock {@link SurfaceControl.Builder} that supports method chaining and return the
     * given {@link SurfaceControl} when calling {@link SurfaceControl.Builder#build()}.
     *
     * @param mockSurfaceControl the first {@link SurfaceControl} to return
     * @return the mock of {@link SurfaceControl.Builder}
     */
    public static SurfaceControl.Builder createMockSurfaceControlBuilder(
            SurfaceControl mockSurfaceControl) {
        final SurfaceControl.Builder mockBuilder = mock(SurfaceControl.Builder.class, RETURNS_SELF);
        doReturn(mockSurfaceControl)
                .when(mockBuilder)
                .build();
        return mockBuilder;
    }

    /**
     * Creates a mock {@link SurfaceControl.Transaction} that supports method chaining.
     * @return the mock of {@link SurfaceControl.Transaction}
     */
    public static SurfaceControl.Transaction createMockSurfaceControlTransaction() {
        return mock(SurfaceControl.Transaction.class, RETURNS_SELF);
    }
}
