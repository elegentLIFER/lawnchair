
package com.android.launcher3.states;

import static com.android.launcher3.Flags.enableScalingRevealHomeAnimation;
import static com.android.launcher3.logging.StatsLogManager.LAUNCHER_STATE_HOME;

import android.content.Context;

import androidx.core.graphics.ColorUtils;

import com.android.launcher3.Launcher;
import com.android.launcher3.LauncherState;

import app.lawnchair.theme.color.tokens.ColorTokens;

/**
 * Scale down workspace/hotseat to hint at going to either overview (on pause) or first home screen.
 */
public class HintState extends LauncherState {

    private static final int STATE_FLAGS = FLAG_WORKSPACE_INACCESSIBLE | FLAG_DISABLE_RESTORE
            | FLAG_HAS_SYS_UI_SCRIM;

    public static final float DEPTH_5_PERCENT = 0.05f;

    public HintState(int id) {
        this(id, LAUNCHER_STATE_HOME);
    }

    public HintState(int id, int statsLogOrdinal) {
        super(id, statsLogOrdinal, STATE_FLAGS);
    }

    @Override
    public int getTransitionDuration(Context context, boolean isToState) {
        return 80;
    }

    @Override
    protected float getDepthUnchecked(Context context) {
        if (enableScalingRevealHomeAnimation()) {
            return DEPTH_5_PERCENT;
        } else {
            return 0.15f;
        }
    }

    @Override
    public int getWorkspaceScrimColor(Launcher launcher) {
        return ColorUtils.setAlphaComponent(
                ColorTokens.OverviewScrim.resolveColor(launcher), 100);
    }

    @Override
    public ScaleAndTranslation getWorkspaceScaleAndTranslation(Launcher launcher) {
        return new ScaleAndTranslation(0.92f, 0, 0);
    }
}
