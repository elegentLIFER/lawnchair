

package com.android.wm.shell.pip2.phone;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.android.wm.shell.R;

/**
 * Container layout wraps single action image view drawn in PiP menu and can restrict the size of
 * action image view (see pip_menu_action.xml).
 */
public class PipMenuActionView extends FrameLayout {
    private ImageView mImageView;
    private View mCustomCloseBackground;

    public PipMenuActionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mImageView = findViewById(R.id.image);
        mCustomCloseBackground = findViewById(R.id.custom_close_bg);
    }

    /** pass through to internal {@link #mImageView} */
    public void setImageDrawable(Drawable drawable) {
        mImageView.setImageDrawable(drawable);
    }

    /** pass through to internal {@link #mCustomCloseBackground} */
    public void setCustomCloseBackgroundVisibility(@Visibility int visibility) {
        mCustomCloseBackground.setVisibility(visibility);
    }
}
