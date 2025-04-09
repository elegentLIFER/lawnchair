

package com.android.launcher3.tapl;

import com.android.launcher3.tapl.LauncherInstrumentation.ContainerType;

/**
 * Represents a special state in Overview where the initial split app is shoved to the side and a
 * second split app can be selected.
 */
public class SplitScreenSelect extends Overview {

    SplitScreenSelect(LauncherInstrumentation launcher) {
        super(launcher);
    }

    @Override
    protected ContainerType getContainerType() {
        return ContainerType.SPLIT_SCREEN_SELECT;
    }

    @Override
    protected boolean isActionsViewVisible() {
        // We don't show overview actions in split select state.
        return false;
    }
}
