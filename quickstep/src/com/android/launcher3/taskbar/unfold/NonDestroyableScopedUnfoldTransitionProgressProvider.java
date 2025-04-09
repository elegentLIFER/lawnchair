
package com.android.launcher3.taskbar.unfold;

import com.android.systemui.unfold.util.ScopedUnfoldTransitionProgressProvider;

/**
 * ScopedUnfoldTransitionProgressProvider that doesn't propagate destroy method
 */
public class NonDestroyableScopedUnfoldTransitionProgressProvider extends
        ScopedUnfoldTransitionProgressProvider {

    @Override
    public void destroy() {
        // no-op
    }
}
