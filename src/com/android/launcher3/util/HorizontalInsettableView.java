
package com.android.launcher3.util;

import android.util.FloatProperty;

/**
 * Allows the implementing view to add insets to the left and right.
 */
public interface HorizontalInsettableView {

    /**
     * Sets left and right insets for the view so it looks like the width of the view is
     * reduced when inset is increased.
     *
     * The inset is calculated based on the width of the view: e.g. when the width of
     * the view is 100px then if we apply 0.15f horizontal inset percentage the rendered width
     * of the view will be 70px with 15px of padding on the left and right sides.
     *
     * @param insetPercentage width percentage to inset the content from the left and from the right
     */
    void setHorizontalInsets(float insetPercentage);

    /**
     * Returns the width percentage to inset the content from the left and from the right. See
     * {@link #setHorizontalInsets};
     */
    float getHorizontalInsets();

    FloatProperty<HorizontalInsettableView> HORIZONTAL_INSETS =
            new FloatProperty<HorizontalInsettableView>("horizontalInsets") {
                @Override
                public Float get(HorizontalInsettableView view) {
                    return view.getHorizontalInsets();
                }

                @Override
                public void setValue(HorizontalInsettableView view, float insetPercentage) {
                    view.setHorizontalInsets(insetPercentage);
                }
            };
}
