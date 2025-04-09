

package com.android.wm.shell.flicker.service.desktopmode.flicker

import android.tools.flicker.AssertionInvocationGroup
import android.tools.flicker.assertors.assertions.AppLayerIsInvisibleAtEnd
import android.tools.flicker.assertors.assertions.AppLayerIsVisibleAlways
import android.tools.flicker.assertors.assertions.AppLayerIsVisibleAtStart
import android.tools.flicker.assertors.assertions.AppWindowHasDesktopModeInitialBoundsAtTheEnd
import android.tools.flicker.assertors.assertions.AppWindowIsVisibleAlways
import android.tools.flicker.assertors.assertions.AppWindowOnTopAtEnd
import android.tools.flicker.assertors.assertions.AppWindowOnTopAtStart
import android.tools.flicker.assertors.assertions.AppWindowRemainInsideDisplayBounds
import android.tools.flicker.assertors.assertions.LauncherWindowMovesToTop
import android.tools.flicker.config.AssertionTemplates
import android.tools.flicker.config.FlickerConfigEntry
import android.tools.flicker.config.ScenarioId
import android.tools.flicker.config.desktopmode.Components
import android.tools.flicker.extractors.ITransitionMatcher
import android.tools.flicker.extractors.ShellTransitionScenarioExtractor
import android.tools.traces.wm.Transition
import android.tools.traces.wm.TransitionType

class DesktopModeFlickerScenarios {
    companion object {
        val END_DRAG_TO_DESKTOP =
            FlickerConfigEntry(
                scenarioId = ScenarioId("END_DRAG_TO_DESKTOP"),
                extractor =
                    ShellTransitionScenarioExtractor(
                        transitionMatcher =
                            object : ITransitionMatcher {
                                override fun findAll(
                                    transitions: Collection<Transition>
                                ): Collection<Transition> {
                                    return transitions.filter {
                                        it.type == TransitionType.DESKTOP_MODE_END_DRAG_TO_DESKTOP
                                    }
                                }
                            }
                    ),
                assertions =
                    AssertionTemplates.COMMON_ASSERTIONS +
                        listOf(
                                AppLayerIsVisibleAlways(Components.DESKTOP_MODE_APP),
                                AppWindowOnTopAtEnd(Components.DESKTOP_MODE_APP),
                                AppWindowHasDesktopModeInitialBoundsAtTheEnd(
                                    Components.DESKTOP_MODE_APP
                                )
                            )
                            .associateBy({ it }, { AssertionInvocationGroup.BLOCKING }),
            )

        val CLOSE_APP =
            FlickerConfigEntry(
                scenarioId = ScenarioId("CLOSE_APP"),
                extractor =
                    ShellTransitionScenarioExtractor(
                        transitionMatcher =
                            object : ITransitionMatcher {
                                override fun findAll(
                                    transitions: Collection<Transition>
                                ): Collection<Transition> {
                                    return transitions.filter { it.type == TransitionType.CLOSE }
                                }
                            }
                    ),
                assertions =
                    AssertionTemplates.COMMON_ASSERTIONS +
                        listOf(
                                AppWindowOnTopAtStart(Components.DESKTOP_MODE_APP),
                                AppLayerIsVisibleAtStart(Components.DESKTOP_MODE_APP),
                                AppLayerIsInvisibleAtEnd(Components.DESKTOP_MODE_APP),
                            )
                            .associateBy({ it }, { AssertionInvocationGroup.BLOCKING }),
            )

        val CLOSE_LAST_APP =
            FlickerConfigEntry(
                scenarioId = ScenarioId("CLOSE_LAST_APP"),
                extractor =
                    ShellTransitionScenarioExtractor(
                        transitionMatcher =
                            object : ITransitionMatcher {
                                override fun findAll(
                                    transitions: Collection<Transition>
                                ): Collection<Transition> {
                                    val lastTransition =
                                        transitions.findLast { it.type == TransitionType.CLOSE }
                                    return if (lastTransition != null) listOf(lastTransition)
                                    else emptyList()
                                }
                            }
                    ),
                assertions =
                    AssertionTemplates.COMMON_ASSERTIONS +
                        listOf(
                                AppWindowOnTopAtStart(Components.DESKTOP_MODE_APP),
                                AppLayerIsVisibleAtStart(Components.DESKTOP_MODE_APP),
                                AppLayerIsInvisibleAtEnd(Components.DESKTOP_MODE_APP),
                                LauncherWindowMovesToTop()
                            )
                            .associateBy({ it }, { AssertionInvocationGroup.BLOCKING }),
            )

        val CORNER_RESIZE =
            FlickerConfigEntry(
                scenarioId = ScenarioId("CORNER_RESIZE"),
                extractor =
                ShellTransitionScenarioExtractor(
                    transitionMatcher =
                    object : ITransitionMatcher {
                        override fun findAll(
                            transitions: Collection<Transition>
                        ): Collection<Transition> {
                            return transitions.filter {
                                it.type == TransitionType.CHANGE
                            }
                        }
                    }
                ),
                assertions =
                        listOf(
                            AppWindowIsVisibleAlways(Components.DESKTOP_MODE_APP),
                            AppWindowOnTopAtEnd(Components.DESKTOP_MODE_APP),
                            AppWindowRemainInsideDisplayBounds(Components.DESKTOP_MODE_APP),
                        ).associateBy({ it }, { AssertionInvocationGroup.BLOCKING }),
            )
    }
}
