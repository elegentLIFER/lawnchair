

package com.android.wm.shell.dagger.pip;

import android.content.Context;

import com.android.wm.shell.dagger.WMSingleton;
import com.android.wm.shell.pip.PipAnimationController;
import com.android.wm.shell.pip.PipSurfaceTransactionHelper;

import dagger.Module;
import dagger.Provides;

/**
 * Provides shared dependencies from {@link com.android.wm.shell.pip}, this implementation is
 * shared with {@link TvPipModule} and possibly other form factors.
 */
@Module
public abstract class Pip1SharedModule {
    @WMSingleton
    @Provides
    static PipSurfaceTransactionHelper providePipSurfaceTransactionHelper(Context context) {
        return new PipSurfaceTransactionHelper(context);
    }

    @WMSingleton
    @Provides
    static PipAnimationController providePipAnimationController(PipSurfaceTransactionHelper
            pipSurfaceTransactionHelper) {
        return new PipAnimationController(pipSurfaceTransactionHelper);
    }
}
