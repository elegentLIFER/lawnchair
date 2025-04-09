
package com.android.launcher3;

import android.view.KeyEvent;
import android.view.View;

import com.android.launcher3.accessibility.LauncherAccessibilityDelegate;
import com.android.launcher3.model.data.ItemInfo;
import com.android.launcher3.uioverrides.PredictedAppIcon;
import com.android.launcher3.uioverrides.QuickstepLauncher;

import java.util.List;

public class QuickstepAccessibilityDelegate extends LauncherAccessibilityDelegate {

    public QuickstepAccessibilityDelegate(QuickstepLauncher launcher) {
        super(launcher);
        mActions.put(PIN_PREDICTION, new LauncherAction(
                PIN_PREDICTION, R.string.pin_prediction, KeyEvent.KEYCODE_P));
    }

    @Override
    protected void getSupportedActions(View host, ItemInfo item, List<LauncherAction> out) {
        if (host instanceof PredictedAppIcon && !((PredictedAppIcon) host).isPinned()) {
            out.add(new LauncherAction(PIN_PREDICTION, R.string.pin_prediction,
                    KeyEvent.KEYCODE_P));
        }
        super.getSupportedActions(host, item, out);
    }

    @Override
    protected boolean performAction(View host, ItemInfo item, int action, boolean fromKeyboard) {
        QuickstepLauncher launcher = (QuickstepLauncher) mContext;
        if (action == PIN_PREDICTION) {
            if (launcher.getHotseatPredictionController() == null) {
                return false;
            }
            launcher.getHotseatPredictionController().pinPrediction(item);
            return true;
        }
        return super.performAction(host, item, action, fromKeyboard);
    }
}
