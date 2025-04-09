
package com.android.launcher3.model;

import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

/**
 * Helper logger that collects logs while {@code LoaderTask#run} executes and prints them all iff
 * an exception is caught in {@code LoaderTask#run}.
 */
public class LoaderMemoryLogger {

    private static final String TAG = "LoaderMemoryLogger";

    private final ArrayList<LogEntry> mLogEntries = new ArrayList<>();

    protected LoaderMemoryLogger() {}

    protected void addLog(int logLevel, String tag, String log) {
        addLog(logLevel, tag, log, null);
    }

    protected void addLog(
            int logLevel, String tag, String log, Exception stackTrace) {
        switch (logLevel) {
            case Log.ASSERT:
            case Log.ERROR:
            case Log.DEBUG:
            case Log.INFO:
            case Log.VERBOSE:
            case Log.WARN:
                mLogEntries.add(new LogEntry(logLevel, tag, log, stackTrace));
                break;
            default:
                throw new IllegalArgumentException("Invalid log level provided: " + logLevel);

        }
    }

    protected void clearLogs() {
        mLogEntries.clear();
    }

    protected void printLogs() {
        for (LogEntry logEntry : mLogEntries) {
            String tag = String.format("%s: %s", TAG, logEntry.mLogTag);
            String logString = logEntry.mStackStrace == null
                    ? logEntry.mLogString
                    : String.format(
                            "%s\n%s",
                            logEntry.mLogString,
                            Log.getStackTraceString(logEntry.mStackStrace));

            Log.println(logEntry.mLogLevel, tag, logString);
        }
        clearLogs();
    }

    private static class LogEntry {

        protected final int mLogLevel;
        protected final String mLogTag;
        protected final String mLogString;
        @Nullable protected final Exception mStackStrace;

        protected LogEntry(
                int logLevel, String logTag, String logString, @Nullable Exception stackStrace) {
            mLogLevel = logLevel;
            mLogTag = logTag;
            mLogString = logString;
            mStackStrace = stackStrace;
        }
    }
}
