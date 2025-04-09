
package com.android.quickstep.util;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceControl;
import android.view.SurfaceControl.Transaction;
import android.view.View;
import android.view.View.OnAttachStateChangeListener;
import android.view.ViewRootImpl;

import com.android.launcher3.Utilities;
import com.android.quickstep.RemoteAnimationTargets.ReleaseCheck;

import app.lawnchair.compat.LawnchairQuickstepCompat;

/**
 * Helper class to apply surface transactions in sync with RenderThread similar to
 *   android.view.SyncRtSurfaceTransactionApplier
 * with some Launcher specific utility methods
 */
@TargetApi(Build.VERSION_CODES.R)
public class SurfaceTransactionApplier extends ReleaseCheck {

    private static final int MSG_UPDATE_SEQUENCE_NUMBER = 0;

    private final Handler mApplyHandler;

    private boolean mInitialized;
    private SurfaceControl mBarrierSurfaceControl;
    private ViewRootImpl mTargetViewRootImpl;

    private int mLastSequenceNumber = 0;

    /**
     * @param targetView The view in the surface that acts as synchronization anchor.
     */
    public SurfaceTransactionApplier(View targetView) {
        if (targetView.isAttachedToWindow()) {
            initialize(targetView);
        } else {
            mInitialized = false;
            targetView.addOnAttachStateChangeListener(new OnAttachStateChangeListener() {
                @Override
                public void onViewAttachedToWindow(View v) {
                    if (!mInitialized) {
                        targetView.removeOnAttachStateChangeListener(this);
                        initialize(targetView);
                    }
                }

                @Override
                public void onViewDetachedFromWindow(View v) {
                    // Do nothing
                }
            });
        }
        mApplyHandler = new Handler(this::onApplyMessage);
        setCanRelease(true);
    }

    private void initialize(View view) {
        mTargetViewRootImpl = view.getViewRootImpl();
        try {
            mBarrierSurfaceControl = mTargetViewRootImpl.getSurfaceControl();
        } catch (Throwable t) {
            // Ignore
        }
        mInitialized = true;
    }

    protected boolean onApplyMessage(Message msg) {
        if (msg.what == MSG_UPDATE_SEQUENCE_NUMBER) {
            setCanRelease(msg.arg1 == mLastSequenceNumber);
            return true;
        }
        return false;
    }

    /**
     * Schedules applying surface parameters on the next frame.
     *
     * @param params The surface parameters to apply. DO NOT MODIFY the list after passing into
     *               this method to avoid synchronization issues.
     */
    public void scheduleApply(SurfaceTransaction params) {
        if (!mInitialized) {
            params.getTransaction().apply();
            return;
        }
        View view = mTargetViewRootImpl.getView();
        if (view == null) {
            return;
        }
        Transaction t = params.getTransaction();

        mLastSequenceNumber++;
        final int toApplySeqNo = mLastSequenceNumber;
        setCanRelease(false);
        mTargetViewRootImpl.registerRtFrameCallback(frame -> {
            if (mBarrierSurfaceControl == null && !Utilities.ATLEAST_Q) return;
            if (mBarrierSurfaceControl == null || !mBarrierSurfaceControl.isValid()) {
                Message.obtain(mApplyHandler, MSG_UPDATE_SEQUENCE_NUMBER, toApplySeqNo, 0)
                        .sendToTarget();
                return;
            }
            if (LawnchairQuickstepCompat.ATLEAST_S) {
                mTargetViewRootImpl.mergeWithNextTransaction(t, frame);
            } else {
                t.apply();
            }
            Message.obtain(mApplyHandler, MSG_UPDATE_SEQUENCE_NUMBER, toApplySeqNo, 0)
                    .sendToTarget();
        });

        // Make sure a frame gets scheduled.
        view.invalidate();
    }
}
