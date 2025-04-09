

package com.android.wm.shell.bubbles.storage

import android.app.ActivityTaskManager.INVALID_TASK_ID
import android.testing.AndroidTestingRunner
import android.util.SparseArray
import androidx.test.filters.SmallTest
import com.android.wm.shell.ShellTestCase
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@SmallTest
@RunWith(AndroidTestingRunner::class)
class BubblePersistentRepositoryTest : ShellTestCase() {

    // user, package, shortcut, notification key, height, res-height, title, taskId, locusId
    private val user0Bubbles = listOf(
            BubbleEntity(0, "com.example.messenger", "shortcut-1", "0k1", 120, 0, null, 1, null,
                    true),
            BubbleEntity(10, "com.example.chat", "alice and bob", "0k2", 0, 16537428, "title", 2,
                    null),
            BubbleEntity(0, "com.example.messenger", "shortcut-2", "0k3", 120, 0, null,
                    INVALID_TASK_ID, null)
    )

    private val user1Bubbles = listOf(
            BubbleEntity(1, "com.example.messenger", "shortcut-1", "1k1", 120, 0, null, 3, null,
                    true),
            BubbleEntity(12, "com.example.chat", "alice and bob", "1k2", 0, 16537428, "title", 4,
                    null),
            BubbleEntity(1, "com.example.messenger", "shortcut-2", "1k3", 120, 0, null,
                    INVALID_TASK_ID, null)
    )

    private val bubbles = SparseArray<List<BubbleEntity>>()

    private lateinit var repository: BubblePersistentRepository

    @Before
    fun setup() {
        repository = BubblePersistentRepository(mContext)
        bubbles.put(0, user0Bubbles)
        bubbles.put(1, user1Bubbles)
    }

    @After
    fun teardown() {
        // Clean up the any persisted bubbles for the next run
        repository.persistsToDisk(SparseArray())
    }

    @Test
    fun testReadWriteOperation() {
        // Verify read before write doesn't cause FileNotFoundException
        val actual = repository.readFromDisk()
        assertNotNull(actual)
        assertEquals(actual.size(), 0)

        repository.persistsToDisk(bubbles)
        assertTrue(bubbles.contentEquals(repository.readFromDisk()))
    }
}
