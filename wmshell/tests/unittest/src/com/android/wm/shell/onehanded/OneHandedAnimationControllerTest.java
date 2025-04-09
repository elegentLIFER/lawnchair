

package com.android.wm.shell.onehanded;

import static org.junit.Assert.assertNotNull;

import android.graphics.Rect;
import android.testing.AndroidTestingRunner;
import android.testing.TestableLooper;
import android.view.SurfaceControl;
import android.window.WindowContainerToken;

import androidx.test.filters.SmallTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Unit tests against {@link OneHandedAnimationController} to ensure that it sends the right
 * callbacks
 * depending on the various interactions.
 */
@RunWith(AndroidTestingRunner.class)
@SmallTest
@TestableLooper.RunWithLooper(setAsMainLooper = true)
public class OneHandedAnimationControllerTest extends OneHandedTestCase {

    OneHandedAnimationController mOneHandedAnimationController;

    @Mock
    private SurfaceControl mMockLeash;
    @Mock
    private WindowContainerToken mMockToken;
    private Rect mDisplayBounds = new Rect();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mOneHandedAnimationController = new OneHandedAnimationController(mContext);
    }

    @Test
    public void testGetAnimator_withSameBounds_returnAnimator() {
        final float yOffset = 300;
        final OneHandedAnimationController.OneHandedTransitionAnimator animator =
                mOneHandedAnimationController
                        .getAnimator(mMockToken, mMockLeash, 0, yOffset, mDisplayBounds);

        assertNotNull(animator);
    }
}
