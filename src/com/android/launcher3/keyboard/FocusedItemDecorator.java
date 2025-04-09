

package com.android.launcher3.keyboard;

import android.graphics.Canvas;
import android.view.View;
import android.view.View.OnFocusChangeListener;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ItemDecoration;
import androidx.recyclerview.widget.RecyclerView.State;

import com.android.launcher3.keyboard.FocusIndicatorHelper.SimpleFocusIndicatorHelper;

/**
 * {@link ItemDecoration} for drawing and animating focused view background.
 */
public class FocusedItemDecorator extends ItemDecoration {

    private FocusIndicatorHelper mHelper;

    public FocusedItemDecorator(View container) {
        mHelper = new SimpleFocusIndicatorHelper(container);
    }

    public FocusedItemDecorator(FocusIndicatorHelper focusIndicatorHelper) {
        mHelper = focusIndicatorHelper;
    }

    public OnFocusChangeListener getFocusListener() {
        return mHelper;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, State state) {
        // Use onDrawOver so focus outline is always visible
        mHelper.draw(c);
    }
}
