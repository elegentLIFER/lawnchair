
package com.android.launcher3.taskbar.allapps;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.android.launcher3.ExtendedEditText;
import com.android.launcher3.allapps.ActivityAllAppsContainerView;
import com.android.launcher3.allapps.SearchUiManager;

/** Empty search container for Taskbar All Apps used as a fallback if search is not supported. */
public class TaskbarAllAppsFallbackSearchContainer extends View implements SearchUiManager {
    public TaskbarAllAppsFallbackSearchContainer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TaskbarAllAppsFallbackSearchContainer(
            Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void initializeSearch(ActivityAllAppsContainerView<?> containerView) {
        // Do nothing.
    }

    @Override
    public void resetSearch() {
        // Do nothing.
    }

    @Nullable
    @Override
    public ExtendedEditText getEditText() {
        return null;
    }
}
