

package com.android.launcher3.accessibility;

import static com.android.launcher3.LauncherState.NORMAL;
import static com.android.launcher3.anim.AnimatorListeners.forSuccessCallback;

import android.view.View;

import com.android.launcher3.AbstractFloatingView;
import com.android.launcher3.Launcher;
import com.android.launcher3.LauncherSettings;
import com.android.launcher3.R;
import com.android.launcher3.model.data.ItemInfo;
import com.android.launcher3.model.data.WorkspaceItemInfo;
import com.android.launcher3.shortcuts.DeepShortcutView;

import java.util.Collections;
import java.util.List;

/**
 * Extension of {@link LauncherAccessibilityDelegate} with actions specific to shortcuts in
 * deep shortcuts menu.
 */
public class ShortcutMenuAccessibilityDelegate extends LauncherAccessibilityDelegate {

    public ShortcutMenuAccessibilityDelegate(Launcher launcher) {
        super(launcher);
    }

    @Override
    protected void getSupportedActions(View host, ItemInfo item, List<LauncherAction> out) {
        if ((host.getParent() instanceof DeepShortcutView)) {
            out.add(mActions.get(ADD_TO_WORKSPACE));
        }
    }

    @Override
    protected boolean performAction(View host, ItemInfo item, int action, boolean fromKeyboard) {
        if (action == ADD_TO_WORKSPACE) {
            if (!(host.getParent() instanceof DeepShortcutView)) {
                return false;
            }
            final WorkspaceItemInfo info = ((DeepShortcutView) host.getParent()).getFinalInfo();
            final int[] coordinates = new int[2];
            final int screenId = findSpaceOnWorkspace(item, coordinates);
            if (screenId == -1) {
                return false;
            }
            mContext.getStateManager().goToState(NORMAL, true, forSuccessCallback(() -> {
                mContext.getModelWriter().addItemToDatabase(info,
                        LauncherSettings.Favorites.CONTAINER_DESKTOP,
                        screenId, coordinates[0], coordinates[1]);
                mContext.bindItems(Collections.singletonList(info), true);
                AbstractFloatingView.closeAllOpenViews(mContext);
                announceConfirmation(R.string.item_added_to_workspace);
            }));
            return true;
        }
        return false;
    }
}
