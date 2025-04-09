

package com.android.launcher3.celllayout.board;

public class CellType {
    // The cells marked by this will be filled by 1x1 widgets and will be ignored when
    // validating
    public static final char IGNORE = 'x';
    // The cells marked by this will be filled by app icons
    public static final char ICON = 'i';
    // The cells marked by FOLDER will be filled by folders with 27 app icons inside
    public static final char FOLDER = 'Z';
    // Empty space
    public static final char EMPTY = '-';
    // Widget that will be saved as "main widget" for easier retrieval
    public static final char MAIN_WIDGET = 'm';
    // Everything else will be consider a widget
}
