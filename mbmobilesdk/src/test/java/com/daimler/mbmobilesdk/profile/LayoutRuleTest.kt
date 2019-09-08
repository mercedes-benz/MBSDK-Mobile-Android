package com.daimler.mbmobilesdk.profile

import com.daimler.mbmobilesdk.profile.creator.ProfileViewableCreator
import com.daimler.mbmobilesdk.profile.fields.ProfileField
import com.daimler.mbmobilesdk.profile.fields.ProfileFieldPreparation
import com.daimler.mbmobilesdk.profile.fields.ProfileViewable
import com.daimler.mbmobilesdk.profile.layout.*
import com.daimler.mbingresskit.common.ProfileFieldUsage
import com.daimler.mbingresskit.common.ProfileSelectableValues
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class LayoutRuleTest : LayoutRuleResolver {

    private lateinit var fields: List<ProfileField>
    private val creator: ProfileViewableCreator = TestViewableCreator()

    @Before
    fun setup() {
        fields = TestPreparation().fields()
    }

    @Test
    fun testSideBySide() {
        val rule1 =
            sideBySide<ProfileField.MobilePhone, ProfileField.LandlinePhone>()
        val rule2 = ProfileFieldLayoutRule.ToSideOf(
            ProfileField.MobilePhone::class.java, ProfileField.LandlinePhone::class.java
        )
        assertEquals(rule1.start, rule2.start)
        assertEquals(rule1.end, rule2.end)
    }

    @Test
    fun testToSideOfRule() {
        val rule =
            sideBySide<ProfileField.MobilePhone, ProfileField.LandlinePhone>()
        val ruleTag = rule.tag
        var ruleAppliedCounter = 0
        val group = object : LayoutGroup<Unit> {

            override fun add(t: Unit) {
                ruleAppliedCounter++
            }

            override fun findByTag(tag: Any?): Unit? {
                assertEquals(true, ruleTag == tag)
                return null
            }
        }
        fields.forEach {
            val res = it.create(creator)
            when (it) {
                is ProfileField.MobilePhone -> {
                    assertEquals(true, rule.canBeAppliedTo(res))
                    assertEquals(true, rule.isStartField(it))
                }
                is ProfileField.LandlinePhone -> {
                    assertEquals(true, rule.canBeAppliedTo(res))
                    assertEquals(true, rule.isEndField(it))
                }
            }
            if (rule.canBeAppliedTo(res)) rule.resolve(group, res, this)
        }
        assertEquals(2, ruleAppliedCounter)
    }

    @Test
    fun testPositionRule() {
        val rule =
            forcePosition<ProfileField.MobilePhone>(0)
        val ruleTag = rule.tag
        var ruleAppliedCounter = 0
        val group = object : LayoutGroup<Unit> {

            override fun add(t: Unit) {
                ruleAppliedCounter++
            }

            override fun findByTag(tag: Any?): Unit? {
                assertEquals(true, ruleTag == tag)
                return null
            }
        }
        fields.forEach {
            val res = it.create(creator)
            when (it) {
                is ProfileField.MobilePhone -> {
                    assertEquals(true, rule.canBeAppliedTo(res))
                }
            }
            if (rule.canBeAppliedTo(res)) rule.resolve(group, res, this)
        }
        assertEquals(1, ruleAppliedCounter)
    }

    override fun <S : ProfileField, E : ProfileField> resolveRule(
        rule: ProfileFieldLayoutRule.ToSideOf<S, E>,
        viewable: ProfileViewable,
        root: LayoutGroup<*>
    ) {
        if (rule.canBeAppliedTo(viewable)) {
            @Suppress("UNCHECKED_CAST")
            (root as LayoutGroup<Unit>).add(Unit)
            root.findByTag(rule.tag)
        }
    }

    override fun <T : ProfileField> resolveRule(
        rule: ProfileFieldLayoutRule.Position<T>,
        viewable: ProfileViewable,
        root: LayoutGroup<*>
    ) {
        if (rule.canBeAppliedTo(viewable)) {
            @Suppress("UNCHECKED_CAST")
            (root as LayoutGroup<Unit>).add(Unit)
            root.findByTag(rule.tag)
        }
    }

    private class TestPreparation : ProfileFieldPreparation {

        override fun fields(): List<ProfileField> {
            var counter = 0
            return listOf(
                ProfileField.Email(ProfileFieldUsage.MANDATORY, counter++, null),
                ProfileField.Salutation(ProfileFieldUsage.MANDATORY, counter++, emptySelectableValues()),
                ProfileField.Title(ProfileFieldUsage.MANDATORY, counter++, emptySelectableValues(), false),
                ProfileField.FirstName(ProfileFieldUsage.MANDATORY, counter++, null),
                ProfileField.LastName(ProfileFieldUsage.MANDATORY, counter++, null),
                ProfileField.LanguageCode(ProfileFieldUsage.MANDATORY, counter++, emptySelectableValues(), false),
                ProfileField.MobilePhone(ProfileFieldUsage.MANDATORY, counter++, null),
                ProfileField.LandlinePhone(ProfileFieldUsage.MANDATORY, counter, null)
            )
        }

        private fun emptySelectableValues() =
            ProfileSelectableValues(false, null, emptyList())
    }
}