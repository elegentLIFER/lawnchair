
package com.android.quickstep.util

import android.animation.ValueAnimator
import android.os.IBinder
import android.os.RemoteException
import android.view.SurfaceControl
import android.view.SurfaceControl.Transaction
import android.window.IRemoteTransitionFinishedCallback
import android.window.RemoteTransitionStub
import android.window.TransitionInfo
import com.android.launcher3.anim.AnimatorListeners.forEndCallback
import com.android.launcher3.util.Executors
import com.android.wm.shell.shared.TransitionUtil

/** Remote animation which fades out the closing targets */
class FadeOutRemoteTransition : RemoteTransitionStub() {

    override fun startAnimation(
        transition: IBinder,
        info: TransitionInfo,
        startT: Transaction,
        finishCB: IRemoteTransitionFinishedCallback
    ) {
        val anim = ValueAnimator.ofFloat(1f, 0f)

        val closingControls: MutableList<SurfaceControl> = mutableListOf()
        for (chg in info.changes) {
            startT.show(chg.leash)
            if (TransitionUtil.isClosingType(chg.mode)) {
                closingControls.add(chg.leash)
            }
        }
        startT.apply()

        anim.addUpdateListener {
            val t = Transaction()
            closingControls.forEach { t.setAlpha(it, anim.animatedValue as Float) }
            t.apply()
        }
        anim.addListener(
            forEndCallback(
                Runnable {
                    val t = Transaction()
                    closingControls.forEach { t.hide(it) }
                    try {
                        finishCB.onTransitionFinished(null, t)
                    } catch (e: RemoteException) {
                        // Ignore
                    }
                }
            )
        )

        Executors.MAIN_EXECUTOR.execute { anim.start() }
    }

    override fun onTransitionConsumed(transition: IBinder?, aborted: Boolean) {}
}
