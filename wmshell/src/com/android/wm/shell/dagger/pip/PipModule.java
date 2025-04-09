

package com.android.wm.shell.dagger.pip;

import com.android.wm.shell.common.pip.PipUtils;
import com.android.wm.shell.dagger.WMSingleton;
import com.android.wm.shell.pip.PipTransitionController;
import com.android.wm.shell.pip2.phone.PipTransition;

import dagger.Module;
import dagger.Provides;

/**
 * Provides dependencies for external components / modules reference PiP and extracts away the
 * selection of legacy and new PiP implementation.
 */
@Module(includes = {
        Pip1Module.class,
        Pip2Module.class
})
public abstract class PipModule {
    @WMSingleton
    @Provides
    static PipTransitionController providePipTransitionController(
            com.android.wm.shell.pip.PipTransition legacyPipTransition,
            PipTransition newPipTransition) {
        if (PipUtils.isPip2ExperimentEnabled()) {
            return newPipTransition;
        } else {
            return legacyPipTransition;
        }
    }
}
