

package com.android.wm.shell.bubbles;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotSame;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.graphics.Color;
import android.graphics.PointF;
import android.testing.AndroidTestingRunner;
import android.testing.TestableLooper;
import android.view.View;
import android.widget.TextView;

import androidx.test.filters.SmallTest;

import com.android.wm.shell.R;
import com.android.wm.shell.ShellTestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@SmallTest
@RunWith(AndroidTestingRunner.class)
@TestableLooper.RunWithLooper(setAsMainLooper = true)
public class BubbleFlyoutViewTest extends ShellTestCase {
    private BubbleFlyoutView mFlyout;
    private TextView mFlyoutText;
    private TextView mSenderName;
    private float[] mDotCenter = new float[2];
    private Bubble.FlyoutMessage mFlyoutMessage;
    @Mock
    private BubblePositioner mPositioner;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(mPositioner.getBubbleSize()).thenReturn(60);

        mFlyoutMessage = new Bubble.FlyoutMessage();
        mFlyoutMessage.senderName = "Josh";
        mFlyoutMessage.message = "Hello";

        mFlyout = new BubbleFlyoutView(getContext(), mPositioner);

        mFlyoutText = mFlyout.findViewById(R.id.bubble_flyout_text);
        mSenderName = mFlyout.findViewById(R.id.bubble_flyout_name);
        mDotCenter[0] = 30;
        mDotCenter[1] = 30;
    }

    @Test
    public void testShowFlyout_isVisible() {
        mFlyout.setupFlyoutStartingAsDot(
                mFlyoutMessage,
                new PointF(100, 100), true, Color.WHITE, null, null, mDotCenter,
                false);
        mFlyout.setVisibility(View.VISIBLE);

        assertEquals("Hello", mFlyoutText.getText());
        assertEquals("Josh", mSenderName.getText());
        assertEquals(View.VISIBLE, mFlyout.getVisibility());
    }

    @Test
    public void testFlyoutHide_runsCallback() {
        Runnable after = mock(Runnable.class);
        mFlyout.setupFlyoutStartingAsDot(mFlyoutMessage,
                new PointF(100, 100), true, Color.WHITE, null, after, mDotCenter,
                false);
        mFlyout.hideFlyout();

        verify(after).run();
    }

    @Test
    public void testSetCollapsePercent() {
        mFlyout.setupFlyoutStartingAsDot(mFlyoutMessage,
                new PointF(100, 100), true, Color.WHITE, null, null, mDotCenter,
                false);
        mFlyout.setVisibility(View.VISIBLE);

        mFlyout.setCollapsePercent(1f);
        assertEquals(0f, mFlyoutText.getAlpha(), 0.01f);
        assertNotSame(0f, mFlyoutText.getTranslationX()); // Should have moved to collapse.

        mFlyout.setCollapsePercent(0f);
        assertEquals(1f, mFlyoutText.getAlpha(), 0.01f);
        assertEquals(0f, mFlyoutText.getTranslationX());
    }
}
