
package com.android.launcher3.celllayout;

import static com.android.launcher3.LauncherSettings.Favorites.CONTAINER_DESKTOP;

import android.view.View;

import com.android.launcher3.CellLayout;
import com.android.launcher3.Launcher;
import com.android.launcher3.celllayout.board.CellLayoutBoard;
import com.android.launcher3.views.DoubleShadowBubbleTextView;

import java.util.ArrayList;
import java.util.List;

public class CellLayoutTestUtils {

    public static ArrayList<CellLayoutBoard> workspaceToBoards(Launcher launcher) {
        ArrayList<CellLayoutBoard> boards = new ArrayList<>();
        for (CellLayout cellLayout : launcher.getWorkspace().mWorkspaceScreens) {

            int count = cellLayout.getShortcutsAndWidgets().getChildCount();
            for (int i = 0; i < count; i++) {
                View callView = cellLayout.getShortcutsAndWidgets().getChildAt(i);
                CellLayoutLayoutParams params =
                        (CellLayoutLayoutParams) callView.getLayoutParams();

                CellPosMapper.CellPos pos = launcher.getCellPosMapper().mapPresenterToModel(
                        params.getCellX(), params.getCellY(),
                        launcher.getWorkspace().getCellLayoutId(cellLayout), CONTAINER_DESKTOP);
                int screenId = pos.screenId;
                for (int j = boards.size(); j <= screenId; j++) {
                    boards.add(new CellLayoutBoard(cellLayout.getCountX(), cellLayout.getCountY()));
                }
                CellLayoutBoard board = boards.get(screenId);
                // is icon
                if (callView instanceof DoubleShadowBubbleTextView) {
                    board.addIcon(pos.cellX, pos.cellY);
                } else {
                    // is widget
                    board.addWidget(pos.cellX, pos.cellY, params.cellHSpan,
                            params.cellVSpan);
                }
            }
        }
        return boards;
    }

    public static CellLayoutBoard viewsToBoard(List<View> views, int width, int height) {
        CellLayoutBoard board = new CellLayoutBoard(width, height);

        for (View callView : views) {
            CellLayoutLayoutParams params = (CellLayoutLayoutParams) callView.getLayoutParams();
            // is icon
            if (callView instanceof DoubleShadowBubbleTextView) {
                board.addIcon(params.getCellX(), params.getCellY());
            } else {
                // is widget
                board.addWidget(params.getCellX(), params.getCellY(), params.cellHSpan,
                        params.cellVSpan);
            }
        }
        return board;
    }
}
