

package com.android.systemui.plugins.qs;

import android.content.Context;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;

import com.android.systemui.plugins.annotations.DependsOn;
import com.android.systemui.plugins.annotations.ProvidesInterface;
import com.android.systemui.plugins.qs.QSTile.State;

@ProvidesInterface(version = QSTileView.VERSION)
@DependsOn(target = QSIconView.class)
@DependsOn(target = QSTile.class)
public abstract class QSTileView extends LinearLayout {
    public static final int VERSION = 3;

    public QSTileView(Context context) {
        super(context);
    }

    public abstract View updateAccessibilityOrder(View previousView);

    /**
     * Returns a {@link QSIconView} containing only the icon for this tile. Use
     * {@link #getIconWithBackground()} to retrieve the entire tile (background & peripherals
     * included).
     */
    public abstract QSIconView getIcon();

    /**
     * Returns a {@link View} containing the icon for this tile along with the accompanying
     * background circle/peripherals. To retrieve only the inner icon, use {@link #getIcon()}.
     */
    public abstract View getIconWithBackground();

    /**
     * Returns the {@link View} containing the icon on the right
     *
     * @see com.android.systemui.qs.tileimpl.QSTileViewHorizontal#sideView
     */
    public View getSecondaryIcon() {
        return null;
    }
    public abstract void init(QSTile tile);
    public abstract void onStateChanged(State state);

    public abstract int getDetailY();

    public View getLabel() {
        return null;
    }

    public View getLabelContainer() {
        return null;
    }

    public View getSecondaryLabel() {
        return null;
    }

    /** Sets the index of this tile in its layout */
    public abstract void setPosition(int position);

    /** Get the duration of a visuo-haptic long-press effect */
    public int getLongPressEffectDuration() {
        return ViewConfiguration.getLongPressTimeout() - ViewConfiguration.getTapTimeout();
    }
}
