
package com.android.launcher3;

import com.android.quickstep.util.ActivityInitListener;

import java.util.function.BiPredicate;

public class LauncherInitListener extends ActivityInitListener<Launcher> {

    /**
     * @param onInitListener a callback made when the activity is initialized. The callback should
     *                       return true to continue receiving callbacks (ie. for if the activity is
     *                       recreated).
     */
    public LauncherInitListener(BiPredicate<Launcher, Boolean> onInitListener) {
        super(onInitListener, Launcher.ACTIVITY_TRACKER);
    }

    @Override
    public boolean handleInit(Launcher launcher, boolean alreadyOnHome) {
        launcher.deferOverlayCallbacksUntilNextResumeOrStop();
        return super.handleInit(launcher, alreadyOnHome);
    }
}
