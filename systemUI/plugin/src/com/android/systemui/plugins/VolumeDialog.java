

package com.android.systemui.plugins;

import com.android.systemui.plugins.VolumeDialog.Callback;
import com.android.systemui.plugins.annotations.DependsOn;
import com.android.systemui.plugins.annotations.ProvidesInterface;

/**
 * This interface is really just a stub for initialization/teardown, actual handling of
 * when to show will be done through {@link VolumeDialogController}
 */
@ProvidesInterface(action = VolumeDialog.ACTION, version = VolumeDialog.VERSION)
@DependsOn(target = Callback.class)
public interface VolumeDialog extends Plugin {
    String ACTION = "com.android.systemui.action.PLUGIN_VOLUME";
    int VERSION = 1;

    void init(int windowType, Callback callback);
    void destroy();

    @ProvidesInterface(version = VERSION)
    public interface Callback {
        int VERSION = 1;

        void onZenSettingsClicked();
        void onZenPrioritySettingsClicked();
    }
}
