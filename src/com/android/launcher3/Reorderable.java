

package com.android.launcher3;

import com.android.launcher3.util.MultiTranslateDelegate;

public interface Reorderable {

    /**
     * Returns the delegate to control translation
     */
    MultiTranslateDelegate getTranslateDelegate();

    /**
     * Set the scale related to reorder hint and "bounce" animations
     */
    void setReorderBounceScale(float scale);

    float getReorderBounceScale();
}
