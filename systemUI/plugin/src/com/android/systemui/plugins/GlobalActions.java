

package com.android.systemui.plugins;

import com.android.systemui.plugins.GlobalActions.GlobalActionsManager;
import com.android.systemui.plugins.annotations.DependsOn;
import com.android.systemui.plugins.annotations.ProvidesInterface;

@ProvidesInterface(action = GlobalActions.ACTION, version = GlobalActions.VERSION)
@DependsOn(target = GlobalActionsManager.class)
public interface GlobalActions extends Plugin {

    String ACTION = "com.android.systemui.action.PLUGIN_GLOBAL_ACTIONS";
    int VERSION = 1;

    void showGlobalActions(GlobalActionsManager manager);
    default void showShutdownUi(boolean isReboot, String reason) {
    }

    default void destroy() {
    }

    @ProvidesInterface(version = GlobalActionsManager.VERSION)
    public interface GlobalActionsManager {
        int VERSION = 1;

        void onGlobalActionsShown();
        void onGlobalActionsHidden();

        void shutdown();
        void reboot(boolean safeMode);
    }
}
