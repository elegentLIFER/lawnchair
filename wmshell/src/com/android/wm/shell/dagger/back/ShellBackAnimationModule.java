

package com.android.wm.shell.dagger.back;

import com.android.wm.shell.back.CrossTaskBackAnimation;
import com.android.wm.shell.back.CustomCrossActivityBackAnimation;
import com.android.wm.shell.back.DefaultCrossActivityBackAnimation;
import com.android.wm.shell.back.ShellBackAnimation;
import com.android.wm.shell.back.ShellBackAnimationRegistry;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/** Default animation definitions for predictive back. */
@Module
public interface ShellBackAnimationModule {
    /** Default animation registry */
    @Provides
    static ShellBackAnimationRegistry provideBackAnimationRegistry(
            @ShellBackAnimation.CrossActivity ShellBackAnimation crossActivity,
            @ShellBackAnimation.CrossTask ShellBackAnimation crossTask,
            @ShellBackAnimation.CustomizeActivity ShellBackAnimation customizeActivity) {
        return new ShellBackAnimationRegistry(
                crossActivity,
                crossTask,
                /* dialogCloseAnimation */ null,
                customizeActivity,
                /* defaultBackToHomeAnimation= */ null);
    }

    /** Default cross activity back animation */
    @Binds
    @ShellBackAnimation.CrossActivity
    ShellBackAnimation bindCrossActivityShellBackAnimation(
            DefaultCrossActivityBackAnimation defaultCrossActivityBackAnimation);

    /** Default cross task back animation */
    @Binds
    @ShellBackAnimation.CrossTask
    ShellBackAnimation provideCrossTaskShellBackAnimation(
            CrossTaskBackAnimation crossTaskBackAnimation);

    /** Default customized activity back animation */
    @Binds
    @ShellBackAnimation.CustomizeActivity
    ShellBackAnimation provideCustomizeActivityShellBackAnimation(
            CustomCrossActivityBackAnimation customCrossActivityBackAnimation);
}
