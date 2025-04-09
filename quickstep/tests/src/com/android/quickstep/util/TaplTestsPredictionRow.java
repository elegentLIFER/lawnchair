

package com.android.quickstep.util;

import com.android.quickstep.AbstractQuickStepTest;

import org.junit.Test;

public class TaplTestsPredictionRow extends AbstractQuickStepTest {

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mLauncher.getWorkspace().switchToAllApps();
    }

    @Test
    public void testPredictionRow() {
        mLauncher.getAllApps().getPredictionRowView();
    }
}
