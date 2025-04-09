

package com.android.systemui.plugin.testoverlayplugin;

import android.annotation.Nullable;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * View with some logging to show that its being run.
 */
public class CustomView extends View {

    private static final String TAG = "CustomView";

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Log.d(TAG, "new instance");
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d(TAG, "onAttachedToWindow");
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d(TAG, "onDetachedFromWindow");
    }
}
