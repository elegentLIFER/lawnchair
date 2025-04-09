

package com.android.wm.shell.splitscreen.tv;

import static android.view.KeyEvent.ACTION_DOWN;
import static android.view.KeyEvent.KEYCODE_BACK;

import static com.android.wm.shell.common.split.SplitScreenConstants.SPLIT_POSITION_BOTTOM_OR_RIGHT;
import static com.android.wm.shell.common.split.SplitScreenConstants.SPLIT_POSITION_TOP_OR_LEFT;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.android.wm.shell.R;
import com.android.wm.shell.common.split.SplitScreenConstants;

/**
 * A View for the Menu Window.
 */
public class TvSplitMenuView extends LinearLayout implements View.OnClickListener {

    private Listener mListener;

    public TvSplitMenuView(Context context) {
        super(context);
    }

    public TvSplitMenuView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TvSplitMenuView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initButtons();
    }

    @Override
    public void onClick(View v) {
        if (mListener == null) return;

        final int id = v.getId();
        if (id == R.id.tv_split_main_menu_focus_button) {
            mListener.onFocusStage(SPLIT_POSITION_TOP_OR_LEFT);
        } else if (id == R.id.tv_split_main_menu_close_button) {
            mListener.onCloseStage(SPLIT_POSITION_TOP_OR_LEFT);
        } else if (id == R.id.tv_split_side_menu_focus_button) {
            mListener.onFocusStage(SPLIT_POSITION_BOTTOM_OR_RIGHT);
        } else if (id == R.id.tv_split_side_menu_close_button) {
            mListener.onCloseStage(SPLIT_POSITION_BOTTOM_OR_RIGHT);
        } else if (id == R.id.tv_split_menu_swap_stages) {
            mListener.onSwapPress();
        }
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == ACTION_DOWN) {
            if (event.getKeyCode() == KEYCODE_BACK) {
                if (mListener != null) {
                    mListener.onBackPress();
                    return true;
                }
            }
        }
        return super.dispatchKeyEvent(event);
    }

    private void initButtons() {
        findViewById(R.id.tv_split_main_menu_focus_button).setOnClickListener(this);
        findViewById(R.id.tv_split_main_menu_close_button).setOnClickListener(this);
        findViewById(R.id.tv_split_side_menu_focus_button).setOnClickListener(this);
        findViewById(R.id.tv_split_side_menu_close_button).setOnClickListener(this);
        findViewById(R.id.tv_split_menu_swap_stages).setOnClickListener(this);
    }

    void setListener(Listener listener) {
        mListener = listener;
    }

    interface Listener {
        /** "Back" button from the remote control */
        void onBackPress();

        /** Menu Action Buttons */

        void onFocusStage(@SplitScreenConstants.SplitPosition int stageToFocus);

        void onCloseStage(@SplitScreenConstants.SplitPosition int stageToClose);

        void onSwapPress();
    }
}
