
package com.android.launcher3.allapps;

import static com.android.launcher3.logging.StatsLogManager.LauncherEvent.LAUNCHER_TURN_ON_WORK_APPS_TAP;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.launcher3.R;
import com.android.launcher3.Utilities;
import com.android.launcher3.model.StringCache;
import com.android.launcher3.views.ActivityContext;

import app.lawnchair.font.FontManager;
import app.lawnchair.theme.color.tokens.ColorTokens;

/**
 * Work profile toggle switch shown at the bottom of AllApps work tab
 */
public class WorkPausedCard extends LinearLayout implements View.OnClickListener {

    private final ActivityContext mActivityContext;

    public WorkPausedCard(Context context) {
        this(context, null, 0);
    }

    public WorkPausedCard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WorkPausedCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mActivityContext = ActivityContext.lookupContext(getContext());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setWorkProfilePausedResources();
    }

    public void setWorkProfilePausedResources() {
        TextView title = findViewById(R.id.work_apps_paused_title);
        title.setText(R.string.work_apps_paused_title);
        title.setTextColor(ColorTokens.TextColorPrimary.resolveColor(getContext()));
        FontManager.INSTANCE.get(getContext()).setCustomFont(title, R.id.font_heading);

        TextView body = findViewById(R.id.work_apps_paused_content);
        body.setText(R.string.work_apps_paused_body);
        body.setTextColor(ColorTokens.TextColorPrimary.resolveColor(getContext()));
        FontManager.INSTANCE.get(getContext()).setCustomFont(title, R.id.font_body_medium);

        Button button = findViewById(R.id.enable_work_apps);
        button.setText(R.string.work_apps_enable_btn_text);
        FontManager.INSTANCE.get(getContext()).setCustomFont(title, R.id.font_button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        setEnabled(false);
        mActivityContext.getAppsView().getWorkManager().setWorkProfileEnabled(true);
        mActivityContext.getStatsLogManager().logger().log(LAUNCHER_TURN_ON_WORK_APPS_TAP);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int orientation = getResources().getConfiguration().orientation;
        getLayoutParams().height = orientation == Configuration.ORIENTATION_PORTRAIT
                ? LayoutParams.MATCH_PARENT
                : LayoutParams.WRAP_CONTENT;
        super.onLayout(changed, l, t, r, b);
    }
}
