
package com.android.launcher3.settings;

import static com.android.launcher3.icons.GraphicsUtils.setColorAlphaBound;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Property;
import android.view.View;

import androidx.preference.Preference;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ItemDecoration;
import androidx.recyclerview.widget.RecyclerView.State;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.android.launcher3.util.Themes;

/**
 * Utility class for highlighting a preference
 */
public class PreferenceHighlighter extends ItemDecoration implements Runnable {

    private static final Property<PreferenceHighlighter, Integer> HIGHLIGHT_COLOR =
            new Property<PreferenceHighlighter, Integer>(Integer.TYPE, "highlightColor") {

                @Override
                public Integer get(PreferenceHighlighter highlighter) {
                    return highlighter.mHighlightColor;
                }

                @Override
                public void set(PreferenceHighlighter highlighter, Integer value) {
                    highlighter.mHighlightColor = value;
                    highlighter.mRv.invalidateItemDecorations();
                }
            };

    private static final long HIGHLIGHT_DURATION = 15000L;
    private static final long HIGHLIGHT_FADE_OUT_DURATION = 500L;
    private static final long HIGHLIGHT_FADE_IN_DURATION = 200L;
    private static final int END_COLOR = setColorAlphaBound(Color.WHITE, 0);

    private final Paint mPaint = new Paint();
    private final RecyclerView mRv;
    private final int mIndex;
    private final Preference mPreference;
    private final RectF mDrawRect = new RectF();

    private boolean mHighLightStarted = false;
    private int mHighlightColor = END_COLOR;

    public PreferenceHighlighter(RecyclerView rv, int index, Preference preference) {
        mRv = rv;
        mIndex = index;
        mPreference = preference;
    }

    @Override
    public void run() {
        mRv.addItemDecoration(this);
        mRv.smoothScrollToPosition(mIndex);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, State state) {
        ViewHolder holder = parent.findViewHolderForAdapterPosition(mIndex);
        if (holder == null) {
            return;
        }
        if (!mHighLightStarted && state.getRemainingScrollVertical() != 0) {
            // Wait until scrolling stopped
            return;
        }

        if (!mHighLightStarted) {
            // Start highlight
            int colorTo = setColorAlphaBound(Themes.getColorAccent(mRv.getContext()), 66);
            ObjectAnimator anim = ObjectAnimator.ofArgb(this, HIGHLIGHT_COLOR, END_COLOR,
                    colorTo);
            anim.setDuration(HIGHLIGHT_FADE_IN_DURATION);
            anim.setRepeatMode(ValueAnimator.REVERSE);
            anim.setRepeatCount(4);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    removeHighlight();
                }
            });
            anim.start();
            mHighLightStarted = true;
        }

        View view = holder.itemView;
        mPaint.setColor(mHighlightColor);
        mDrawRect.set(0, view.getY(), parent.getWidth(), view.getY() + view.getHeight());
        if (mPreference instanceof HighlightDelegate) {
            ((HighlightDelegate) mPreference).offsetHighlight(view, mDrawRect);
        }
        c.drawRect(mDrawRect, mPaint);
    }

    private void removeHighlight() {
        ObjectAnimator anim = ObjectAnimator.ofArgb(
                this, HIGHLIGHT_COLOR, mHighlightColor, END_COLOR);
        anim.setDuration(HIGHLIGHT_FADE_OUT_DURATION);
        anim.setStartDelay(HIGHLIGHT_DURATION);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mRv.removeItemDecoration(PreferenceHighlighter.this);
            }
        });
        anim.start();
    }

    /**
     * Interface to be implemented by a preference to customize the highlight are
     */
    public interface HighlightDelegate {

        /**
         * Allows the preference to update the highlight area
         */
        void offsetHighlight(View prefView, RectF bounds);

    }
}
