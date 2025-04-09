

package com.android.launcher3.allapps;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.launcher3.R;
import com.android.launcher3.views.ActivityContext;

public class FloatingMaskView extends ConstraintLayout {

    private final ActivityContext mActivityContext;
    private ImageView mBottomBox;

    public FloatingMaskView(Context context) {
        this(context, null, 0);
    }

    public FloatingMaskView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatingMaskView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mActivityContext = ActivityContext.lookupContext(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mBottomBox = findViewById(R.id.bottom_box);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) getLayoutParams();
        AllAppsRecyclerView allAppsContainerView =
                mActivityContext.getAppsView().getActiveRecyclerView();
        if (lp != null) {
            lp.rightMargin = allAppsContainerView.getPaddingRight();
            lp.leftMargin = allAppsContainerView.getPaddingLeft();
            mBottomBox.setMinimumHeight(allAppsContainerView.getPaddingBottom());
        }
    }
}
