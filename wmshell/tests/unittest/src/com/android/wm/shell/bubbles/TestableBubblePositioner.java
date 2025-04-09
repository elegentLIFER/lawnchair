

package com.android.wm.shell.bubbles;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Insets;
import android.graphics.Rect;
import android.view.WindowManager;

import com.android.wm.shell.R;

public class TestableBubblePositioner extends BubblePositioner {
    private int mMaxBubbles;

    public TestableBubblePositioner(Context context,
            WindowManager windowManager) {
        super(context, windowManager);

        updateInternal(Configuration.ORIENTATION_PORTRAIT,
                Insets.of(0, 0, 0, 0),
                new Rect(0, 0, 500, 1000));
        mMaxBubbles = context.getResources().getInteger(R.integer.bubbles_max_rendered);
    }

    public void setMaxBubbles(int max) {
        mMaxBubbles = max;
    }

    @Override
    public int getMaxBubbles() {
        return mMaxBubbles;
    }
}
