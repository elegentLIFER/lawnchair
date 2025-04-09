

package com.android.wm.shell.back;

import android.content.res.Configuration;
import android.window.BackNavigationInfo;

import javax.inject.Qualifier;

/** Base class for all back animations. */
public abstract class ShellBackAnimation {
    @Qualifier
    public @interface CrossActivity {}

    @Qualifier
    public @interface CrossTask {}

    @Qualifier
    public @interface CustomizeActivity {}

    @Qualifier
    public @interface ReturnToHome {}

    @Qualifier
    public @interface DialogClose {}

    /** Retrieve the {@link BackAnimationRunner} associated with this animation. */
    public abstract BackAnimationRunner getRunner();

    /**
     * Prepare the next animation.
     *
     * @return true if this type of back animation should override the default.
     */
    public boolean prepareNextAnimation(BackNavigationInfo.CustomAnimationInfo animationInfo,
            int letterboxColor) {
        return false;
    }

    void onConfigurationChanged(Configuration newConfig) {

    }
}
