
package com.android.launcher3.tapl;

import static com.android.launcher3.testing.shared.TestProtocol.ALL_APPS_STATE_ORDINAL;

import androidx.test.uiautomator.UiObject2;

/**
 * Operations on Home screen qsb.
 */
class HomeQsb extends Qsb {

    HomeQsb(LauncherInstrumentation launcher, UiObject2 hotseat) {
        super(launcher, hotseat, "search_container_hotseat");
    }

    @Override
    protected void clickQsb() {
        // Clicking Qsb will switch to All Apps state.
        mLauncher.runToState(
                () -> super.clickQsb(),
                ALL_APPS_STATE_ORDINAL,
                "Clicking Qsb");
    }
}
