

package com.android.wm.shell.splitscreen;

import static com.android.wm.shell.common.split.SplitScreenConstants.SPLIT_POSITION_BOTTOM_OR_RIGHT;
import static com.android.wm.shell.splitscreen.SplitScreenController.EXIT_REASON_UNKNOWN;

import com.android.wm.shell.sysui.ShellCommandHandler;

import java.io.PrintWriter;

/**
 * Handles the shell commands for the SplitscreenController.
 */
public class SplitScreenShellCommandHandler implements
        ShellCommandHandler.ShellCommandActionHandler {

    private final SplitScreenController mController;

    public SplitScreenShellCommandHandler(SplitScreenController controller) {
        mController = controller;
    }

    @Override
    public boolean onShellCommand(String[] args, PrintWriter pw) {
        switch (args[0]) {
            case "moveToSideStage":
                return runMoveToSideStage(args, pw);
            case "removeFromSideStage":
                return runRemoveFromSideStage(args, pw);
            case "setSideStagePosition":
                return runSetSideStagePosition(args, pw);
            case "switchSplitPosition":
                return runSwitchSplitPosition();
            case "exitSplitScreen":
                return runExitSplitScreen(args, pw);
            default:
                pw.println("Invalid command: " + args[0]);
                return false;
        }
    }

    private boolean runMoveToSideStage(String[] args, PrintWriter pw) {
        if (args.length < 3) {
            // First argument is the action name.
            pw.println("Error: task id should be provided as arguments");
            return false;
        }
        final int taskId = new Integer(args[1]);
        final int sideStagePosition = args.length > 2
                ? new Integer(args[2]) : SPLIT_POSITION_BOTTOM_OR_RIGHT;
        mController.moveToSideStage(taskId, sideStagePosition);
        return true;
    }

    private boolean runRemoveFromSideStage(String[] args, PrintWriter pw) {
        if (args.length < 2) {
            // First argument is the action name.
            pw.println("Error: task id should be provided as arguments");
            return false;
        }
        final int taskId = new Integer(args[1]);
        mController.removeFromSideStage(taskId);
        return true;
    }

    private boolean runSetSideStagePosition(String[] args, PrintWriter pw) {
        if (args.length < 2) {
            // First argument is the action name.
            pw.println("Error: side stage position should be provided as arguments");
            return false;
        }
        final int position = new Integer(args[1]);
        mController.setSideStagePosition(position);
        return true;
    }

    private boolean runSwitchSplitPosition() {
        mController.switchSplitPosition("shellCommand");
        return true;
    }

    private boolean runExitSplitScreen(String[] args, PrintWriter pw) {
        if (args.length < 2) {
            // First argument is the action name.
            pw.println("Error: task id should be provided as arguments");
            return false;
        }
        final int taskId = Integer.parseInt(args[1]);
        mController.exitSplitScreen(taskId, EXIT_REASON_UNKNOWN);
        return true;
    }

    @Override
    public void printShellCommandHelp(PrintWriter pw, String prefix) {
        pw.println(prefix + "moveToSideStage <taskId> <SideStagePosition>");
        pw.println(prefix + "  Move a task with given id in split-screen mode.");
        pw.println(prefix + "removeFromSideStage <taskId>");
        pw.println(prefix + "  Remove a task with given id in split-screen mode.");
        pw.println(prefix + "setSideStagePosition <SideStagePosition>");
        pw.println(prefix + "  Sets the position of the side-stage.");
        pw.println(prefix + "switchSplitPosition");
        pw.println(prefix + "  Reverses the split.");
        pw.println(prefix + "exitSplitScreen <taskId>");
        pw.println(prefix + "  Exits split screen and leaves the provided split task on top.");
    }
}
