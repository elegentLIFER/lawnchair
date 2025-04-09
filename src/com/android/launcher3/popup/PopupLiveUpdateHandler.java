
package com.android.launcher3.popup;

import android.content.Context;
import android.view.View;

import com.android.launcher3.views.ActivityContext;

/**
 * Utility class to handle updates while the popup is visible (like widgets and
 * notification changes)
 *
 * @param <T> The activity on which the popup shows
 */
public abstract class PopupLiveUpdateHandler<T extends Context & ActivityContext> implements
        PopupDataProvider.PopupDataChangeListener, View.OnAttachStateChangeListener {

    protected final T mContext;
    protected final PopupContainerWithArrow<T> mPopupContainerWithArrow;

    public PopupLiveUpdateHandler(
            T context, PopupContainerWithArrow<T> popupContainerWithArrow) {
        mContext = context;
        mPopupContainerWithArrow = popupContainerWithArrow;
    }

    @Override
    public void onViewAttachedToWindow(View view) {
        PopupDataProvider popupDataProvider = mContext.getPopupDataProvider();

        if (popupDataProvider != null) {
            popupDataProvider.setChangeListener(this);
        }
    }

    @Override
    public void onViewDetachedFromWindow(View view) {
        PopupDataProvider popupDataProvider = mContext.getPopupDataProvider();

        if (popupDataProvider != null) {
            popupDataProvider.setChangeListener(null);
        }
    }
}
