
package com.android.launcher3.allapps;

import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.android.launcher3.allapps.AlphabeticalAppsList.FastScrollSectionInfo;

public class AllAppsFastScrollHelper {

    private static final int NO_POSITION = -1;

    private int mTargetFastScrollPosition = NO_POSITION;

    private AllAppsRecyclerView mRv;
    private ViewHolder mLastSelectedViewHolder;

    public AllAppsFastScrollHelper(AllAppsRecyclerView rv) {
        mRv = rv;
    }

    /**
     * Smooth scrolls the recycler view to the given section.
     */
    public void smoothScrollToSection(FastScrollSectionInfo info) {
        if (mTargetFastScrollPosition == info.position) {
            return;
        }
        mTargetFastScrollPosition = info.position;
        mRv.getLayoutManager().startSmoothScroll(new MyScroller(mTargetFastScrollPosition));
    }

    public void onFastScrollCompleted() {
        mTargetFastScrollPosition = NO_POSITION;
        setLastHolderSelected(false);
        mLastSelectedViewHolder = null;
    }


    private void setLastHolderSelected(boolean isSelected) {
        if (mLastSelectedViewHolder != null) {
            mLastSelectedViewHolder.itemView.setActivated(isSelected);
            mLastSelectedViewHolder.setIsRecyclable(!isSelected);
        }
    }

    private class MyScroller extends LinearSmoothScroller {

        private final int mTargetPosition;

        public MyScroller(int targetPosition) {
            super(mRv.getContext());

            mTargetPosition = targetPosition;
            setTargetPosition(targetPosition);
        }

        @Override
        protected int getVerticalSnapPreference() {
            return SNAP_TO_ANY;
        }

        @Override
        protected void onStop() {
            super.onStop();
            if (mTargetPosition != mTargetFastScrollPosition) {
                // Target changed, before the last scroll can finish
                return;
            }

            ViewHolder currentHolder = mRv.findViewHolderForAdapterPosition(mTargetPosition);
            if (currentHolder == mLastSelectedViewHolder) {
                return;
            }

            setLastHolderSelected(false);
            mLastSelectedViewHolder = currentHolder;
            setLastHolderSelected(true);
        }

        @Override
        protected void onStart() {
            super.onStart();
            if (mTargetPosition != mTargetFastScrollPosition) {
                setLastHolderSelected(false);
                mLastSelectedViewHolder = null;
            }
        }
    }
}
