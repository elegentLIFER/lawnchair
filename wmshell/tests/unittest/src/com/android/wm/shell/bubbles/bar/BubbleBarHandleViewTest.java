
package com.android.wm.shell.bubbles.bar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.graphics.drawable.ColorDrawable;
import android.testing.AndroidTestingRunner;
import android.testing.TestableLooper;

import androidx.core.content.ContextCompat;
import androidx.test.filters.SmallTest;

import com.android.wm.shell.R;
import com.android.wm.shell.ShellTestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@SmallTest
@RunWith(AndroidTestingRunner.class)
@TestableLooper.RunWithLooper
public class BubbleBarHandleViewTest extends ShellTestCase {
    private BubbleBarHandleView mHandleView;

    @Before
    public void setup() {
        mHandleView = new BubbleBarHandleView(mContext);
    }

    @Test
    public void testUpdateHandleColor_lightBg() {
        mHandleView.updateHandleColor(false /* isRegionDark */, false /* animated */);

        assertTrue(mHandleView.getClipToOutline());
        assertTrue(mHandleView.getBackground() instanceof ColorDrawable);
        ColorDrawable bgDrawable = (ColorDrawable) mHandleView.getBackground();
        assertEquals(bgDrawable.getColor(),
                ContextCompat.getColor(mContext, R.color.bubble_bar_expanded_view_handle_dark));
    }

    @Test
    public void testUpdateHandleColor_darkBg() {
        mHandleView.updateHandleColor(true /* isRegionDark */, false /* animated */);

        assertTrue(mHandleView.getClipToOutline());
        assertTrue(mHandleView.getBackground() instanceof ColorDrawable);
        ColorDrawable bgDrawable = (ColorDrawable) mHandleView.getBackground();
        assertEquals(bgDrawable.getColor(),
                ContextCompat.getColor(mContext, R.color.bubble_bar_expanded_view_handle_light));
    }
}
