

package com.android.launcher3.allapps;

import static com.android.launcher3.LauncherSettings.Favorites.CONTAINER_PRIVATESPACE;
import static com.android.launcher3.logging.StatsLogManager.LauncherEvent.LAUNCHER_PRIVATE_SPACE_SETTINGS_TAP;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;

import com.android.launcher3.logging.StatsLogManager;
import com.android.launcher3.model.data.AppInfo;
import com.android.launcher3.util.ApiWrapper;
import com.android.launcher3.views.ActivityContext;

public class PrivateSpaceSettingsButton extends ImageButton implements View.OnClickListener {

    private final ActivityContext mActivityContext;
    private final StatsLogManager mStatsLogManager;
    private final Intent mPrivateSpaceSettingsIntent;

    public PrivateSpaceSettingsButton(Context context) {
        this(context, null, 0);
    }

    public PrivateSpaceSettingsButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PrivateSpaceSettingsButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mActivityContext = ActivityContext.lookupContext(context);
        mStatsLogManager = mActivityContext.getStatsLogManager();
        mPrivateSpaceSettingsIntent =
                ApiWrapper.INSTANCE.get(context).getPrivateSpaceSettingsIntent();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        mStatsLogManager.logger().log(LAUNCHER_PRIVATE_SPACE_SETTINGS_TAP);
        AppInfo privateSpaceSettingsItemInfo = createPrivateSpaceSettingsAppInfo();
        view.setTag(privateSpaceSettingsItemInfo);
        mActivityContext.startActivitySafely(
                view,
                mPrivateSpaceSettingsIntent,
                privateSpaceSettingsItemInfo);
    }

    AppInfo createPrivateSpaceSettingsAppInfo() {
        AppInfo itemInfo = new AppInfo();
        itemInfo.id = CONTAINER_PRIVATESPACE;
        if (mPrivateSpaceSettingsIntent != null) {
            itemInfo.componentName = mPrivateSpaceSettingsIntent.getComponent();
        }
        itemInfo.container = CONTAINER_PRIVATESPACE;
        return itemInfo;
    }
}
