
package com.android.launcher3.folder;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;

import com.android.launcher3.model.data.AppInfo;
import com.android.launcher3.model.data.WorkspaceItemInfo;
import com.android.launcher3.util.ActivityContextWrapper;
import com.android.launcher3.util.Executors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

@SmallTest
@RunWith(AndroidJUnit4.class)
public final class FolderNameProviderTest {
    private Context mContext;
    private WorkspaceItemInfo mItem1;
    private WorkspaceItemInfo mItem2;

    @Before
    public void setUp() {
        mContext = new ActivityContextWrapper(getApplicationContext());
        mItem1 = new WorkspaceItemInfo(new AppInfo(
                new ComponentName("a.b.c", "a.b.c/a.b.c.d"),
                "title1",
                UserHandle.of(10),
                new Intent().setComponent(new ComponentName("a.b.c", "a.b.c/a.b.c.d"))
        ));
        mItem2 = new WorkspaceItemInfo(new AppInfo(
                new ComponentName("a.b.c", "a.b.c/a.b.c.d"),
                "title2",
                UserHandle.of(10),
                new Intent().setComponent(new ComponentName("a.b.c", "a.b.c/a.b.c.d"))
        ));
    }

    @Test
    public void getSuggestedFolderName_workAssignedToEnd() throws Exception {
        ArrayList<WorkspaceItemInfo> list = new ArrayList<>();
        list.add(mItem1);
        list.add(mItem2);
        FolderNameInfos nameInfos = new FolderNameInfos();
        Executors.MODEL_EXECUTOR.submit(() ->
                new FolderNameProvider().getSuggestedFolderName(mContext, list, nameInfos)).get();
        assertEquals("Work", nameInfos.getLabels()[0]);

        nameInfos.setLabel(0, "candidate1", 1.0f);
        nameInfos.setLabel(1, "candidate2", 1.0f);
        nameInfos.setLabel(2, "candidate3", 1.0f);
        Executors.MODEL_EXECUTOR.submit(() ->
                new FolderNameProvider().getSuggestedFolderName(mContext, list, nameInfos)).get();
        assertEquals("Work", nameInfos.getLabels()[3]);
        assertTrue(nameInfos.hasSuggestions());
        assertTrue(nameInfos.hasPrimary());
    }
}
