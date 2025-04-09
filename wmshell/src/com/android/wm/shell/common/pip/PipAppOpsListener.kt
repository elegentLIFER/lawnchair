
package com.android.wm.shell.common.pip

import android.app.AppOpsManager
import android.content.Context
import android.content.pm.PackageManager
import com.android.wm.shell.common.ShellExecutor

class PipAppOpsListener(
    private val mContext: Context,
    private val mCallback: Callback,
    private val mMainExecutor: ShellExecutor
) {
    private val mAppOpsManager: AppOpsManager = checkNotNull(
        mContext.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager)
    private val mAppOpsChangedListener = AppOpsManager.OnOpChangedListener { _, packageName ->
        try {
            // Dismiss the PiP once the user disables the app ops setting for that package
            val topPipActivityInfo = PipUtils.getTopPipActivity(mContext)
            val componentName = topPipActivityInfo.first ?: return@OnOpChangedListener
            val userId = topPipActivityInfo.second
            val appInfo = mContext.packageManager
                .getApplicationInfoAsUser(packageName, 0, userId)
            if (appInfo.packageName == componentName.packageName &&
                mAppOpsManager.checkOpNoThrow(
                    AppOpsManager.OP_PICTURE_IN_PICTURE, appInfo.uid,
                    packageName
                ) != AppOpsManager.MODE_ALLOWED
            ) {
                mMainExecutor.execute { mCallback.dismissPip() }
            }
        } catch (e: PackageManager.NameNotFoundException) {
            // Unregister the listener if the package can't be found
            unregisterAppOpsListener()
        }
    }

    fun onActivityPinned(packageName: String) {
        // Register for changes to the app ops setting for this package while it is in PiP
        registerAppOpsListener(packageName)
    }

    fun onActivityUnpinned() {
        // Unregister for changes to the previously PiP'ed package
        unregisterAppOpsListener()
    }

    private fun registerAppOpsListener(packageName: String) {
        mAppOpsManager.startWatchingMode(
            AppOpsManager.OP_PICTURE_IN_PICTURE, packageName,
            mAppOpsChangedListener
        )
    }

    private fun unregisterAppOpsListener() {
        mAppOpsManager.stopWatchingMode(mAppOpsChangedListener)
    }

    /** Callback for PipAppOpsListener to request changes to the PIP window.  */
    interface Callback {
        /** Dismisses the PIP window.  */
        fun dismissPip()
    }
}
