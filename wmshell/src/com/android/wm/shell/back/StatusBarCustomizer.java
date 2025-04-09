

package com.android.wm.shell.back;

import com.android.internal.view.AppearanceRegion;

/**
 * Interface to customize the system bar color.
 */
public interface StatusBarCustomizer {
    /**
     * Called when the status bar color needs to be changed.
     * @param appearance The region of appearance.
     */
    void customizeStatusBarAppearance(AppearanceRegion appearance);
}
