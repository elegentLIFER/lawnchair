
package com.android.launcher3.allapps;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.view.View;

import com.android.systemui.plugins.AllAppsRow;

/**
 * Wrapper over an {@link AllAppsRow} plugin with {@link FloatingHeaderRow} interface so that
 * it can be easily added in {@link FloatingHeaderView}.
 */
public class PluginHeaderRow implements FloatingHeaderRow {

    private final AllAppsRow mPlugin;
    final View mView;

    PluginHeaderRow(AllAppsRow plugin, FloatingHeaderView parent) {
        mPlugin = plugin;
        mView = mPlugin.setup(parent);
    }

    @Override
    public void setup(FloatingHeaderView parent, FloatingHeaderRow[] allRows,
            boolean tabsHidden) { }

    @Override
    public int getExpectedHeight() {
        return mPlugin.getExpectedHeight();
    }

    @Override
    public boolean shouldDraw() {
        return true;
    }

    @Override
    public boolean hasVisibleContent() {
        return true;
    }

    @Override
    public void setVerticalScroll(int scroll, boolean isScrolledOut) {
        mView.setVisibility(isScrolledOut ? INVISIBLE : VISIBLE);
        if (!isScrolledOut) {
            mView.setTranslationY(scroll);
        }
    }

    @Override
    public Class<PluginHeaderRow> getTypeClass() {
        return PluginHeaderRow.class;
    }

    @Override
    public View getFocusedChild() {
        return null;
    }
}
