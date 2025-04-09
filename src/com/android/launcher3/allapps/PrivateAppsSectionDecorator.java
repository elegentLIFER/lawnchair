

package com.android.launcher3.allapps;

import android.graphics.Canvas;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;

/**
 * Decorator which changes the background color for Private Space Icon Rows in AllAppsContainer.
 */
public class PrivateAppsSectionDecorator extends RecyclerView.ItemDecoration {

    private static final String PRIVATE_APP_SECTION = "private_apps";
    private final AlphabeticalAppsList<?> mAppsList;

    public PrivateAppsSectionDecorator(AlphabeticalAppsList<?> appsList) {
        mAppsList = appsList;
    }

    /** Decorates Private Space Header and Icon Rows to give the shape of a container. */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        HashMap<String, SectionDecorationHandler.UnionDecorationHandler> deferredDecorations =
                new HashMap<>();
        for (int i = 0; i < parent.getChildCount(); i++) {
            View view = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(view);
            if (position < 0 || position >= mAppsList.getAdapterItems().size()) {
                continue;
            }
            BaseAllAppsAdapter.AdapterItem adapterItem = mAppsList.getAdapterItems().get(position);
            SectionDecorationInfo info = adapterItem.decorationInfo;
            if (info == null) {
                continue;
            }
            SectionDecorationHandler decorationHandler = info.getDecorationHandler();
            if (info.shouldDecorateItemsTogether()) {
                SectionDecorationHandler.UnionDecorationHandler unionHandler =
                        deferredDecorations.getOrDefault(
                                PRIVATE_APP_SECTION,
                                new SectionDecorationHandler.UnionDecorationHandler(
                                        decorationHandler, parent.getPaddingLeft(),
                                        parent.getPaddingRight()));
                unionHandler.addChild(decorationHandler, view);
                deferredDecorations.put(PRIVATE_APP_SECTION, unionHandler);
            } else {
                decorationHandler.onFocusDraw(c, view);
            }
        }
        for (SectionDecorationHandler.UnionDecorationHandler decorationHandler
                : deferredDecorations.values()) {
            decorationHandler.onGroupDecorate(c);
        }
    }
}
