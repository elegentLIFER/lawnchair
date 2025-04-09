

package com.android.systemui.animation

/**
 * A base class to easily create an implementation of [ActivityTransitionAnimator.Controller] which
 * delegates most of its call to [delegate]. This is mostly useful for Java code which can't easily
 * create such a delegated class.
 */
open class DelegateTransitionAnimatorController(
    protected val delegate: ActivityTransitionAnimator.Controller
) : ActivityTransitionAnimator.Controller by delegate
