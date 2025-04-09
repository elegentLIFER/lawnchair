

package com.android.launcher3.views;

import static com.android.launcher3.LauncherState.ALL_APPS;
import static com.android.launcher3.LauncherState.NORMAL;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction;

import androidx.annotation.Nullable;

import com.android.launcher3.Launcher;
import com.android.launcher3.LauncherState;
import com.android.launcher3.R;
import com.android.launcher3.statemanager.StateManager.StateListener;
import com.android.launcher3.views.OptionsPopupView.OptionItem;

/**
 * Placeholder view to expose additional Launcher actions via accessibility actions
 */
public class AccessibilityActionsView extends View implements StateListener<LauncherState> {

    public AccessibilityActionsView(Context context) {
        this(context, null);
    }

    public AccessibilityActionsView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AccessibilityActionsView(Context context, @Nullable AttributeSet attrs,
            int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Launcher.getLauncher(context).getStateManager().addStateListener(this);
        setWillNotDraw(true);
    }

    @Override
    public void onStateTransitionComplete(LauncherState finalState) {
        setImportantForAccessibility(finalState == NORMAL
                ? IMPORTANT_FOR_ACCESSIBILITY_YES : IMPORTANT_FOR_ACCESSIBILITY_NO);
    }

    @Override
    public AccessibilityNodeInfo createAccessibilityNodeInfo() {
        AccessibilityNodeInfo info = super.createAccessibilityNodeInfo();
        Launcher l = Launcher.getLauncher(getContext());
        info.addAction(new AccessibilityAction(
                R.string.all_apps_button_label, l.getText(R.string.all_apps_button_label)));
        for (OptionItem item : OptionsPopupView.getOptions(l)) {
            info.addAction(new AccessibilityAction(item.labelRes, item.label));
        }
        return info;
    }

    @Override
    public boolean performAccessibilityAction(int action, Bundle arguments) {
        if (super.performAccessibilityAction(action, arguments)) {
            return true;
        }
        Launcher l = Launcher.getLauncher(getContext());
        if (action == R.string.all_apps_button_label) {
            l.getStatsLogManager().keyboardStateManager().setLaunchedFromA11y(true);
            l.getStateManager().goToState(ALL_APPS);
            return true;
        }
        for (OptionItem item : OptionsPopupView.getOptions(l)) {
            if (item.labelRes == action) {
                if (item.eventId.getId() > 0) {
                    l.getStatsLogManager().logger().log(item.eventId);
                }
                if (item.clickListener.onLongClick(this)) {
                    return true;
                }
            }
        }
        return false;
    }
}
