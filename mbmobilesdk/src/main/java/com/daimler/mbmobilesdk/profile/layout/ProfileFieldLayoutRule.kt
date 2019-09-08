package com.daimler.mbmobilesdk.profile.layout

import com.daimler.mbmobilesdk.profile.fields.ProfileField
import com.daimler.mbmobilesdk.profile.fields.ProfileViewable
import com.daimler.mbmobilesdk.profile.layout.ProfileFieldLayoutRule.Position

/**
 * Collection of rules for the layout for profile fields.
 * NOTE: The order matters, e.g. always specify [Position] rules first.
 */
sealed class ProfileFieldLayoutRule : LayoutRuleResolvable {

    /**
     * Returns true if the given [viewable] can be applied to this rule.
     */
    abstract fun canBeAppliedTo(viewable: ProfileViewable): Boolean

    /**
     * Returns a string that represents this rule according to its related fields.
     */
    abstract val tag: String

    /**
     * Returns the amount of views this rule can be applied to or [Integer.MAX_VALUE] if
     * it is undefined.
     */
    abstract val handledViewAmount: Int

    /**
     * Specifies that two items should be placed next to each other.
     */
    class ToSideOf<S : ProfileField, E : ProfileField>(
        val start: Class<S>,
        val end: Class<E>
    ) : ProfileFieldLayoutRule() {

        override val tag: String = "${start.simpleName}.${end.simpleName}"
        override val handledViewAmount: Int = 2

        override fun canBeAppliedTo(viewable: ProfileViewable): Boolean {
            val cls = viewable.associatedField()?.let { it::class.java }
            return cls?.let { start == it || end == it } ?: false
        }

        override fun resolve(root: LayoutGroup<*>, viewable: ProfileViewable, resolver: LayoutRuleResolver) {
            resolver.resolveRule(this, viewable, root)
        }

        fun isStartField(field: ProfileField) = field::class.java == start

        fun isEndField(field: ProfileField) = field::class.java == end
    }

    /**
     * Specifies that one item should be placed at the given position.
     */
    class Position<T : ProfileField>(
        private val cls: Class<T>,
        val position: Int
    ) : ProfileFieldLayoutRule() {

        override val tag: String = "${cls.simpleName}_$position"
        override val handledViewAmount: Int = 1

        override fun canBeAppliedTo(viewable: ProfileViewable): Boolean {
            val cls = viewable.associatedField()?.let { it::class.java }
            return cls?.let { this.cls == it } == true
        }

        override fun resolve(root: LayoutGroup<*>, viewable: ProfileViewable, resolver: LayoutRuleResolver) {
            resolver.resolveRule(this, viewable, root)
        }
    }
}

internal inline fun <reified T : ProfileField, reified S : ProfileField> sideBySide() =
    ProfileFieldLayoutRule.ToSideOf(T::class.java, S::class.java)

internal inline fun <reified T : ProfileField> forcePosition(position: Int) =
    ProfileFieldLayoutRule.Position(T::class.java, position)