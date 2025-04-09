

package com.android.wm.shell.compatui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.wm.shell.R;

/**
 * Custom layout for Letterbox Education dialog action.
 */
class LetterboxEduDialogActionLayout extends FrameLayout {

    public LetterboxEduDialogActionLayout(Context context) {
        this(context, null);
    }

    public LetterboxEduDialogActionLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LetterboxEduDialogActionLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public LetterboxEduDialogActionLayout(Context context, AttributeSet attrs, int defStyleAttr,
            int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        TypedArray styledAttributes =
                context.getTheme().obtainStyledAttributes(
                        attrs, R.styleable.LetterboxEduDialogActionLayout, defStyleAttr,
                        defStyleRes);
        int iconId = styledAttributes.getResourceId(
                R.styleable.LetterboxEduDialogActionLayout_icon, 0);
        String text = styledAttributes.getString(
                R.styleable.LetterboxEduDialogActionLayout_text);
        styledAttributes.recycle();

        View rootView = inflate(getContext(), R.layout.letterbox_education_dialog_action_layout,
                this);
        ((ImageView) rootView.findViewById(
                R.id.letterbox_education_dialog_action_icon)).setImageResource(iconId);
        ((TextView) rootView.findViewById(R.id.letterbox_education_dialog_action_text)).setText(
                text);
    }
}
