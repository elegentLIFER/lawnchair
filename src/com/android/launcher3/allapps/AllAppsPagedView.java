package com.android.launcher3.allapps;

import static com.android.launcher3.logging.StatsLogManager.LauncherEvent.LAUNCHER_ALLAPPS_SWIPE_TO_PERSONAL_TAB;
import static com.android.launcher3.logging.StatsLogManager.LauncherEvent.LAUNCHER_ALLAPPS_SWIPE_TO_WORK_TAB;

import android.content.Context;
import android.util.AttributeSet;

import com.android.launcher3.PagedView;
import com.android.launcher3.views.ActivityContext;

/**
 * A {@link PagedView} for showing different views for the personal and work profile respectively
 * in the {@link BaseAllAppsContainerView}.
 */
public class AllAppsPagedView extends AllAppsRealPagedView {

    public AllAppsPagedView(Context context) {
        this(context, null);
    }

    public AllAppsPagedView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AllAppsPagedView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected boolean snapToPageWithVelocity(int whichPage, int velocity) {
        boolean resp = super.snapToPageWithVelocity(whichPage, velocity);
        if (resp && whichPage != mCurrentPage) {
            ActivityContext.lookupContext(getContext()).getStatsLogManager().logger()
                    .log(mCurrentPage < whichPage
                            ? LAUNCHER_ALLAPPS_SWIPE_TO_WORK_TAB
                            : LAUNCHER_ALLAPPS_SWIPE_TO_PERSONAL_TAB);
        }
        return resp;
    }
}
