

package com.android.app.viewcapture;

import android.tracing.perfetto.CreateIncrementalStateArgs;
import android.tracing.perfetto.DataSource;
import android.tracing.perfetto.DataSourceInstance;
import android.tracing.perfetto.FlushCallbackArguments;
import android.tracing.perfetto.StartCallbackArguments;
import android.tracing.perfetto.StopCallbackArguments;
import android.util.proto.ProtoInputStream;

import java.util.HashMap;
import java.util.Map;

class ViewCaptureDataSource
extends DataSource<DataSourceInstance, Void, ViewCaptureDataSource.IncrementalState> {
    public static String DATA_SOURCE_NAME = "android.viewcapture";

    private final Runnable mOnStartStaticCallback;
    private final Runnable mOnFlushStaticCallback;
    private final Runnable mOnStopStaticCallback;

    ViewCaptureDataSource(Runnable onStart, Runnable onFlush, Runnable onStop) {
        super(DATA_SOURCE_NAME);
        this.mOnStartStaticCallback = onStart;
        this.mOnFlushStaticCallback = onFlush;
        this.mOnStopStaticCallback = onStop;
    }

    @Override
    public IncrementalState createIncrementalState(
        CreateIncrementalStateArgs<DataSourceInstance> args) {
        return new IncrementalState();
    }

    public static class IncrementalState {
        public final Map<String, Integer> mInternMapPackageName = new HashMap<>();
        public final Map<String, Integer> mInternMapWindowName = new HashMap<>();
        public final Map<String, Integer> mInternMapViewId = new HashMap<>();
        public final Map<String, Integer> mInternMapClassName = new HashMap<>();
        public boolean mHasNotifiedClearedState = false;
    }

    @Override
    public DataSourceInstance createInstance(ProtoInputStream configStream, int instanceIndex) {
        return new DataSourceInstance(this, instanceIndex) {
        @Override
        protected void onStart(StartCallbackArguments args) {
            mOnStartStaticCallback.run();
        }

        @Override
        protected void onFlush(FlushCallbackArguments args) {
            mOnFlushStaticCallback.run();
        }

        @Override
        protected void onStop(StopCallbackArguments args) {
            mOnStopStaticCallback.run();
        }
    };
    }
}
