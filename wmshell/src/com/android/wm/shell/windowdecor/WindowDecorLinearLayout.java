

package com.android.wm.shell.windowdecor;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.android.wm.shell.R;

/**
 * A {@link LinearLayout} that takes an additional task focused drawable state. The new state is
 * used to select the correct background color for views in the window decoration.
 */
public class WindowDecorLinearLayout extends LinearLayout implements TaskFocusStateConsumer {
    private static final int[] TASK_FOCUSED_STATE = { R.attr.state_task_focused };

    private boolean mIsTaskFocused;

    public WindowDecorLinearLayout(Context context) {
        super(context);
    }

    public WindowDecorLinearLayout(Context context,
            @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WindowDecorLinearLayout(Context context, @Nullable AttributeSet attrs,
            int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public WindowDecorLinearLayout(Context context, AttributeSet attrs, int defStyleAttr,
            int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void setTaskFocusState(boolean focused) {
        mIsTaskFocused = focused;

        refreshDrawableState();
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        if (!mIsTaskFocused) {
            return super.onCreateDrawableState(extraSpace);
        }

        final int[] states = super.onCreateDrawableState(extraSpace + 1);
        mergeDrawableStates(states, TASK_FOCUSED_STATE);
        return states;
    }
}
