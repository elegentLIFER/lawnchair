
package com.android.quickstep.util;

import android.util.Log;

import com.android.systemui.shared.recents.model.Task.TaskKey;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;

/**
 * A simple LRU cache for task key entries
 * @param <V> The type of the value
 */
public class TaskKeyLruCache<V> implements TaskKeyCache<V> {

    private final MyLinkedHashMap<V> mMap;

    public TaskKeyLruCache(int maxSize) {
        mMap = new MyLinkedHashMap<>(maxSize);
    }

    /**
     * Removes all entries from the cache
     */
    public synchronized void evictAll() {
        mMap.clear();
    }

    /**
     * Removes a particular entry from the cache
     */
    public synchronized void remove(TaskKey key) {
        mMap.remove(key.id);
    }

    /**
     * Removes all entries matching keyCheck
     */
    public synchronized void removeAll(Predicate<TaskKey> keyCheck) {
        mMap.entrySet().removeIf(e -> keyCheck.test(e.getValue().mKey));
    }

    /**
     * Gets the entry if it is still valid
     */
    public synchronized V getAndInvalidateIfModified(TaskKey key) {
        Entry<V> entry = mMap.get(key.id);

        if (entry != null && entry.mKey.windowingMode == key.windowingMode
                && entry.mKey.lastActiveTime == key.lastActiveTime) {
            return entry.mValue;
        } else {
            remove(key);
            return null;
        }
    }

    /**
     * Adds an entry to the cache, optionally evicting the last accessed entry
     */
    public final synchronized void put(TaskKey key, V value) {
        if (key != null && value != null) {
            mMap.put(key.id, new Entry<>(key, value));
        } else {
            Log.e("TaskKeyCache", "Unexpected null key or value: " + key + ", " + value);
        }
    }

    /**
     * Updates the cache entry if it is already present in the cache
     */
    public synchronized void updateIfAlreadyInCache(int taskId, V data) {
        Entry<V> entry = mMap.get(taskId);
        if (entry != null) {
            entry.mValue = data;
        }
    }

    @Override
    public int getMaxSize() {
        return mMap.mMaxSize;
    }

    @Override
    public int getSize() {
        return mMap.size();
    }

    private static class MyLinkedHashMap<V> extends LinkedHashMap<Integer, Entry<V>> {

        private final int mMaxSize;

        MyLinkedHashMap(int maxSize) {
            super(0, 0.75f, true /* accessOrder */);
            mMaxSize = maxSize;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<Integer, TaskKeyLruCache.Entry<V>> eldest) {
            return size() > mMaxSize;
        }
    }
}
