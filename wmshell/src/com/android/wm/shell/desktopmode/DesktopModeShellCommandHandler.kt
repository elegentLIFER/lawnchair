

package com.android.wm.shell.desktopmode

import com.android.wm.shell.common.desktopmode.DesktopModeTransitionSource.UNKNOWN
import com.android.wm.shell.sysui.ShellCommandHandler
import java.io.PrintWriter

/** Handles the shell commands for the DesktopTasksController. */
class DesktopModeShellCommandHandler(private val controller: DesktopTasksController) :
    ShellCommandHandler.ShellCommandActionHandler {

    override fun onShellCommand(args: Array<String>, pw: PrintWriter): Boolean {
        return when (args[0]) {
            "moveToDesktop" -> {
                if (!runMoveToDesktop(args, pw)) {
                    pw.println("Task not found. Please enter a valid taskId.")
                    false
                } else {
                    true
                }
            }
            "moveToNextDisplay" -> {
                if (!runMoveToNextDisplay(args, pw)) {
                    pw.println("Task not found. Please enter a valid taskId.")
                    false
                } else {
                    true
                }
            }
            else -> {
                pw.println("Invalid command: ${args[0]}")
                false
            }
        }
    }

    private fun runMoveToDesktop(args: Array<String>, pw: PrintWriter): Boolean {
        if (args.size < 2) {
            // First argument is the action name.
            pw.println("Error: task id should be provided as arguments")
            return false
        }

        val taskId =
            try {
                args[1].toInt()
            } catch (e: NumberFormatException) {
                pw.println("Error: task id should be an integer")
                return false
            }

        return controller.moveToDesktop(taskId, transitionSource = UNKNOWN)
    }

    private fun runMoveToNextDisplay(args: Array<String>, pw: PrintWriter): Boolean {
        if (args.size < 2) {
            // First argument is the action name.
            pw.println("Error: task id should be provided as arguments")
            return false
        }

        val taskId =
            try {
                args[1].toInt()
            } catch (e: NumberFormatException) {
                pw.println("Error: task id should be an integer")
                return false
            }

        controller.moveToNextDisplay(taskId)
        return true
    }

    override fun printShellCommandHelp(pw: PrintWriter, prefix: String) {
        pw.println("$prefix moveToDesktop <taskId> ")
        pw.println("$prefix  Move a task with given id to desktop mode.")
        pw.println("$prefix moveToNextDisplay <taskId> ")
        pw.println("$prefix  Move a task with given id to next display.")
    }
}
