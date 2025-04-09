
package com.android.quickstep;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.content.Context;
import android.content.res.Resources;

import androidx.test.filters.SmallTest;

import com.android.launcher3.R;
import com.android.quickstep.util.TaskKeyCache;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.Executor;

@SmallTest
public class TaskThumbnailCacheTest {
    @Mock
    private Context mContext;

    @Mock
    private Resources mResource;

    @Mock
    private TaskKeyCache mTaskKeyCache;

    @Before
    public void setup() throws NoSuchFieldException {
        MockitoAnnotations.initMocks(this);
        when(mContext.getResources()).thenReturn(mResource);
    }

    @Test
    public void increaseCacheSize() {
        // Mock a cache size increase from 3 to 8
        when(mTaskKeyCache.getMaxSize()).thenReturn(3);
        when(mResource.getInteger((R.integer.recentsThumbnailCacheSize))).thenReturn(8);
        TaskThumbnailCache thumbnailCache = new TaskThumbnailCache(mContext, mock(Executor.class),
                mTaskKeyCache);

        // Preload is needed when increasing size
        assertTrue(thumbnailCache.updateCacheSizeAndRemoveExcess());
        verify(mTaskKeyCache, times(1)).updateCacheSizeAndRemoveExcess(8);
    }

    @Test
    public void decreaseCacheSize() {
        // Mock a cache size decrease from 8 to 3
        when(mTaskKeyCache.getMaxSize()).thenReturn(8);
        when(mResource.getInteger((R.integer.recentsThumbnailCacheSize))).thenReturn(3);
        TaskThumbnailCache thumbnailCache = new TaskThumbnailCache(mContext, mock(Executor.class),
                mTaskKeyCache);
        // Preload is not needed when decreasing size
        assertFalse(thumbnailCache.updateCacheSizeAndRemoveExcess());
        verify(mTaskKeyCache, times(1)).updateCacheSizeAndRemoveExcess(3);
    }

    @Test
    public void keepSameCacheSize() {
        when(mTaskKeyCache.getMaxSize()).thenReturn(3);
        when(mResource.getInteger((R.integer.recentsThumbnailCacheSize))).thenReturn(3);
        TaskThumbnailCache thumbnailCache = new TaskThumbnailCache(mContext, mock(Executor.class),
                mTaskKeyCache);
        // Preload is not needed when it has the same cache size
        assertFalse(thumbnailCache.updateCacheSizeAndRemoveExcess());
        verify(mTaskKeyCache, never()).updateCacheSizeAndRemoveExcess(anyInt());
    }
}
