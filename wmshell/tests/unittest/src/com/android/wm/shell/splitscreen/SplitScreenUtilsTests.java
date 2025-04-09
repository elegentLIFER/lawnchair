

package com.android.wm.shell.splitscreen;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.content.res.Configuration;
import android.graphics.Rect;

import androidx.test.filters.SmallTest;
import androidx.test.runner.AndroidJUnit4;

import com.android.wm.shell.ShellTestCase;
import com.android.wm.shell.common.split.SplitScreenUtils;

import org.junit.Test;
import org.junit.runner.RunWith;


/** Tests for {@link com.android.wm.shell.common.split.SplitScreenUtils} */
@SmallTest
@RunWith(AndroidJUnit4.class)
public class SplitScreenUtilsTests extends ShellTestCase {

    @Test
    public void testIsLeftRightSplit() {
        Configuration portraitTablet = new Configuration();
        portraitTablet.smallestScreenWidthDp = 720;
        portraitTablet.windowConfiguration.setMaxBounds(new Rect(0, 0, 500, 1000));
        Configuration landscapeTablet = new Configuration();
        landscapeTablet.smallestScreenWidthDp = 720;
        landscapeTablet.windowConfiguration.setMaxBounds(new Rect(0, 0, 1000, 500));
        Configuration portraitPhone = new Configuration();
        portraitPhone.smallestScreenWidthDp = 420;
        portraitPhone.windowConfiguration.setMaxBounds(new Rect(0, 0, 500, 1000));
        Configuration landscapePhone = new Configuration();
        landscapePhone.smallestScreenWidthDp = 420;
        landscapePhone.windowConfiguration.setMaxBounds(new Rect(0, 0, 1000, 500));

        // Allow L/R split in portrait = false
        assertTrue(SplitScreenUtils.isLeftRightSplit(false /* allowLeftRightSplitInPortrait */,
                landscapeTablet));
        assertTrue(SplitScreenUtils.isLeftRightSplit(false /* allowLeftRightSplitInPortrait */,
                landscapePhone));
        assertFalse(SplitScreenUtils.isLeftRightSplit(false /* allowLeftRightSplitInPortrait */,
                portraitTablet));
        assertFalse(SplitScreenUtils.isLeftRightSplit(false /* allowLeftRightSplitInPortrait */,
                portraitPhone));

        // Allow L/R split in portrait = true, only affects large screens
        assertFalse(SplitScreenUtils.isLeftRightSplit(true /* allowLeftRightSplitInPortrait */,
                landscapeTablet));
        assertTrue(SplitScreenUtils.isLeftRightSplit(true /* allowLeftRightSplitInPortrait */,
                landscapePhone));
        assertTrue(SplitScreenUtils.isLeftRightSplit(true /* allowLeftRightSplitInPortrait */,
                portraitTablet));
        assertFalse(SplitScreenUtils.isLeftRightSplit(true /* allowLeftRightSplitInPortrait */,
                portraitPhone));
    }
}
