
package com.android.launcher3.allapps;

import android.content.Context;
import android.util.AttributeSet;

import com.android.launcher3.DeviceProfile;
import com.android.launcher3.secondarydisplay.SecondaryDisplayLauncher;

/**
 * AllAppsContainerView for secondary launcher
 */
public class SecondaryLauncherAllAppsContainerView extends
        ActivityAllAppsContainerView<SecondaryDisplayLauncher> {

    public SecondaryLauncherAllAppsContainerView(Context context) {
        this(context, null);
    }

    public SecondaryLauncherAllAppsContainerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SecondaryLauncherAllAppsContainerView(Context context, AttributeSet attrs,
            int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void updateBackgroundVisibility(DeviceProfile deviceProfile) {}

    @Override
    public boolean isInAllApps() {
        return mActivityContext.isAppDrawerShown();
    }
}
