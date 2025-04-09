

package com.android.launcher3.uioverrides.states;

import android.content.Context;

import com.android.launcher3.Launcher;
import com.android.quickstep.util.SplitAnimationTimings;
import com.android.quickstep.views.RecentsView;

/**
 * New Overview substate representing state where 1 app for split screen has been selected and
 * pinned and user is selecting the second one
 */
public class SplitScreenSelectState extends OverviewState {
    public SplitScreenSelectState(int id) {
        super(id);
    }

    @Override
    public int getVisibleElements(Launcher launcher) {
        return SPLIT_PLACHOLDER_VIEW;
    }

    @Override
    public float getSplitSelectTranslation(Launcher launcher) {
        RecentsView recentsView = launcher.getOverviewPanel();
        return recentsView.getSplitSelectTranslation();
    }

    @Override
    public int getTransitionDuration(Context context, boolean isToState) {
        boolean isTablet = ((Launcher) context).getDeviceProfile().isTablet;
        if (isToState && isTablet) {
            return SplitAnimationTimings.TABLET_ENTER_DURATION;
        } else if (isToState && !isTablet) {
            return SplitAnimationTimings.PHONE_ENTER_DURATION;
        } else {
            return SplitAnimationTimings.ABORT_DURATION;
        }
    }

    @Override
    public boolean shouldPreserveDataStateOnReapply() {
        return true;
    }
}
