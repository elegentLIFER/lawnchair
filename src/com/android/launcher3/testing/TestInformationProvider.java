

package com.android.launcher3.testing;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.android.launcher3.Utilities;

public class TestInformationProvider extends ContentProvider {

    private static final String TAG = "TestInformationProvider";

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        return null;
    }

    @Override
    public Bundle call(String method, String arg, Bundle extras) {
        if (Utilities.isRunningInTestHarness()) {
            TestInformationHandler handler = TestInformationHandler.newInstance(getContext());
            handler.init(getContext());

            Bundle response =  handler.call(method, arg, extras);
            if (response == null) {
                Log.e(TAG, "Couldn't handle method: " + method + "; current handler="
                        + handler.getClass().getSimpleName());
            }
            return response;
        }
        return null;
    }
}
