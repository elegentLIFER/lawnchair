

package com.android.launcher3.model

/** Data model for the information used for [FirstScreenBroadcastHelper] Broadcast Extras */
data class FirstScreenBroadcastModel(
    // Package name of the installer for all items
    val installerPackage: String,
    // Installing items in Folders
    val pendingCollectionItems: MutableSet<String> = mutableSetOf(),
    // Installing items on first screen
    val pendingWorkspaceItems: MutableSet<String> = mutableSetOf(),
    // Installing items on hotseat
    val pendingHotseatItems: MutableSet<String> = mutableSetOf(),
    // Installing widgets on first screen
    val pendingWidgetItems: MutableSet<String> = mutableSetOf(),
    // Installed/Archived Items on first screen
    val installedWorkspaceItems: MutableSet<String> = mutableSetOf(),
    // Installed/Archived items on hotseat
    val installedHotseatItems: MutableSet<String> = mutableSetOf(),
    // Installed/Archived Widgets on first screen
    val firstScreenInstalledWidgets: MutableSet<String> = mutableSetOf(),
    // Installed Archived Widgets on secondary screens
    val secondaryScreenInstalledWidgets: MutableSet<String> = mutableSetOf()
)
