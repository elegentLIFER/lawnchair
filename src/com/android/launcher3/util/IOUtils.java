

package com.android.launcher3.util;

import android.os.FileUtils;
import android.util.Log;

import com.android.launcher3.Utilities;
import com.android.launcher3.config.FeatureFlags;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Supports various IO utility functions
 */
public class IOUtils {

    private static final int BUF_SIZE = 0x1000; // 4K
    private static final String TAG = "IOUtils";

    public static byte[] toByteArray(File file) throws IOException {
        try (InputStream in = new FileInputStream(file)) {
            return toByteArray(in);
        }
    }

    public static byte[] toByteArray(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        copy(in, out);
        return out.toByteArray();
    }

    public static long copy(InputStream from, OutputStream to) throws IOException {
        if (Utilities.ATLEAST_Q) {
            return FileUtils.copy(from, to);
        } else {
            byte[] buffer = new byte[8192];
            int bytesRead;
            long totalBytes = 0;

            while ((bytesRead = from.read(buffer)) != -1) {
                to.write(buffer, 0, bytesRead);
                totalBytes += bytesRead;
            }
            return totalBytes;
        }
    }

    public static void closeSilently(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException e) {
                if (FeatureFlags.IS_STUDIO_BUILD) {
                    Log.d(TAG, "Error closing", e);
                }
            }
        }
    }
}
