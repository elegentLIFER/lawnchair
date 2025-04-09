
package com.android.wm.shell.performance

import android.content.Context
import android.os.PerformanceHintManager
import android.os.Process
import android.window.SystemPerformanceHinter
import com.android.wm.shell.RootTaskDisplayAreaOrganizer
import com.android.wm.shell.sysui.ShellCommandHandler
import com.android.wm.shell.sysui.ShellInit
import java.io.PrintWriter
import java.util.concurrent.TimeUnit

/**
 * Manages the performance hints to the system.
 */
class PerfHintController(private val mContext: Context,
                         shellInit: ShellInit,
                         private val mShellCommandHandler: ShellCommandHandler,
                         rootTdaOrganizer: RootTaskDisplayAreaOrganizer) {

    // The system perf hinter
    val hinter: SystemPerformanceHinter

    init {
        hinter = SystemPerformanceHinter(mContext,
                rootTdaOrganizer.performanceRootProvider)
        shellInit.addInitCallback(this::onInit, this)
    }

    private fun onInit() {
        mShellCommandHandler.addDumpCallback(this::dump, this)
        val perfHintMgr = mContext.getSystemService(PerformanceHintManager::class.java)
        val adpfSession = perfHintMgr!!.createHintSession(intArrayOf(Process.myTid()),
                TimeUnit.SECONDS.toNanos(1))
        hinter.setAdpfSession(adpfSession)
    }

    fun dump(pw: PrintWriter, prefix: String?) {
        hinter.dump(pw, prefix)
    }
}
