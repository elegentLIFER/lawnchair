
package com.android.launcher3.views;

import android.graphics.Path;
import android.view.View;

/**
 * Alternative to using {@link View#getClipToOutline()} as it only works with derivatives of
 * rounded rect.
 */
public interface ClipPathView {
    void setClipPath(Path clipPath);
}
