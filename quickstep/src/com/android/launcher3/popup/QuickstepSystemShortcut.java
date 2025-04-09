
package com.android.launcher3.popup;

import android.view.View;

import com.android.launcher3.model.data.ItemInfo;
import com.android.launcher3.splitscreen.SplitShortcut;
import com.android.launcher3.uioverrides.QuickstepLauncher;
import com.android.launcher3.util.SplitConfigurationOptions.SplitPositionOption;

/** {@link SystemShortcut.Factory} implementation to create workspace split shortcuts */
public interface QuickstepSystemShortcut {

    String TAG = "QuickstepSystemShortcut";

    static SystemShortcut.Factory<QuickstepLauncher> getSplitSelectShortcutByPosition(
            SplitPositionOption position) {
        return (activity, itemInfo, originalView) ->
                new QuickstepSystemShortcut.SplitSelectSystemShortcut(activity, itemInfo,
                        originalView, position);
    }

    class SplitSelectSystemShortcut extends SplitShortcut<QuickstepLauncher> {

        public SplitSelectSystemShortcut(QuickstepLauncher launcher, ItemInfo itemInfo,
                View originalView, SplitPositionOption position) {
            super(position.iconResId, position.textResId, launcher, itemInfo, originalView,
                    position);
        }
    }
}
