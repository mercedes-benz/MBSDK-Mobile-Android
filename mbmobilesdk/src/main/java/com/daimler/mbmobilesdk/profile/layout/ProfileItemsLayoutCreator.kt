package com.daimler.mbmobilesdk.profile.layout

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daimler.mbmobilesdk.profile.fields.ProfileField
import com.daimler.mbmobilesdk.profile.fields.ProfileRecyclerViewable
import com.daimler.mbmobilesdk.profile.fields.ProfileViewable
import com.daimler.mbmobilesdk.profile.items.BaseProfileRecyclerItem
import com.daimler.mbmobilesdk.utils.ifNotNull

/**
 * [ProfileLayoutCreator] for recycler view items that implement [ProfileRecyclerViewable].
 *
 * NOTE: Runtime changes of the item set of the underlying adapter for the RecyclerView
 * returned by [createLayoutStructure] are currently not supported! The behaviour would be
 * undefined.
 */
internal class ProfileItemsLayoutCreator private constructor(
    context: Context,
    private val rules: List<ProfileFieldLayoutRule>
) : LayoutRuleResolver, ProfileLayoutCreator<ProfileRecyclerViewable, ProfileFieldRecyclerView> {

    private var _context: Context? = context
    private val context: Context
        get() = _context ?: throw IllegalStateException("Context is null.")

    private val sideRuleResolver = SideRuleResolver()

    override fun createLayoutStructure(items: List<ProfileRecyclerViewable>): ProfileFieldRecyclerView {
        // Create the LayoutGroup and add all items to it.
        val group = ItemsLayoutGroup()
        items.forEach { resolvable -> resolvable.item()?.let { group.add(it) } }
        // Resolve layout rules.
        group.resolveRules(rules, this)

        // Create the RecyclerView.
        val recyclerView = ProfileFieldRecyclerView(context, ProfileFieldRecyclerView.ProfileFieldAdapter(group.items))
        recyclerView.layoutManager = ProfileLayoutManager(context, GRID_MAX_SPANS,
            sideRuleResolver.positions())

        sideRuleResolver.clear()

        return recyclerView
    }

    /*
    We need to move the item set for the RecyclerView in a way that items that shall follow
    the ToSideOf rule are in the correct order in the item set of the adapter.
     */
    override fun <S : ProfileField, E : ProfileField> resolveRule(
        rule: ProfileFieldLayoutRule.ToSideOf<S, E>,
        viewable: ProfileViewable,
        root: LayoutGroup<*>
    ) {
        sideRuleResolver.resolve(rule, viewable, root as ItemsLayoutGroup)
    }

    override fun <T : ProfileField> resolveRule(
        rule: ProfileFieldLayoutRule.Position<T>,
        viewable: ProfileViewable,
        root: LayoutGroup<*>
    ) {
        root as ItemsLayoutGroup
        viewable.associatedField()?.let {
            root.moveTo(it, rule.position)
        }
    }

    fun destroy() {
        _context = null
    }

    class Builder(private val context: Context) {

        private val rules = mutableListOf<ProfileFieldLayoutRule>()

        fun rule(rule: ProfileFieldLayoutRule): Builder {
            rules.add(rule)
            return this
        }

        fun build(): ProfileItemsLayoutCreator {
            return ProfileItemsLayoutCreator(context, rules)
        }
    }

    /**
     * GridLayoutManager with dynamic column count.
     *
     * @param context context
     * @param spanCount max column count
     * @param smallPositions a list with item positions which should only take half the width of the other items
     */
    private class ProfileLayoutManager(
        context: Context,
        spanCount: Int,
        private val smallPositions: List<Int>
    ) : GridLayoutManager(context, spanCount) {

        init {
            orientation = RecyclerView.VERTICAL
            spanSizeLookup = SpanSizeLookup()
        }

        private inner class SpanSizeLookup : GridLayoutManager.SpanSizeLookup() {

            override fun getSpanSize(position: Int): Int {
                return if (smallPositions.contains(position)) spanCount / 2 else spanCount
            }
        }
    }

    /**
     * [LayoutGroup] that holds a list of recycler items and provides utility methods to
     * manipulate the item set.
     */
    private class ItemsLayoutGroup : LayoutGroup<BaseProfileRecyclerItem> {

        private val _items = mutableListOf<BaseProfileRecyclerItem>()
        val items: List<BaseProfileRecyclerItem>
            get() = _items

        override fun add(t: BaseProfileRecyclerItem) {
            _items.add(t)
        }

        override fun findByTag(tag: Any?): BaseProfileRecyclerItem? {
            return null
        }

        /**
         * Moves the [secondElem] a position after [firstElem].
         *
         * @return the new indexes of the both elements
         */
        fun moveAfter(firstElem: BaseProfileRecyclerItem, secondElem: BaseProfileRecyclerItem): Pair<Int, Int> {
            val firstIndex = _items.indexOf(firstElem)
            val secondIndex = _items.indexOf(secondElem)
            if (firstIndex != -1 && secondIndex != -1) {
                moveToPosition(secondIndex, firstIndex + 1)
            }
            return _items.indexOf(firstElem) to _items.indexOf(secondElem)
        }

        fun moveToPosition(oldIndex: Int, newIndex: Int) {
            if (oldIndex < newIndex) {
                _items.add(newIndex, _items[oldIndex])
                _items.removeAt(oldIndex)
            } else if (oldIndex > newIndex) {
                val item = _items.removeAt(oldIndex)
                _items.add(newIndex, item)
            }
        }

        fun moveTo(field: ProfileField, position: Int): Boolean {
            val index = _items.indexOfFirst { it.associatedField() == field }
            return if (index != -1) {
                moveToPosition(index, position)
                true
            } else {
                false
            }
        }

        fun resolveRules(rules: List<ProfileFieldLayoutRule>, resolver: LayoutRuleResolver) {
            val original = ArrayList(_items)
            rules.forEach { rule ->
                original.forEach {
                    if (rule.canBeAppliedTo(it)) rule.resolve(this, it, resolver)
                }
            }
        }
    }

    /**
     * Helper class to apply the ToSideOf rule to the item set.
     */
    private class SideRuleResolver {

        private val sidePositions = mutableListOf<Int>()
        private val sideRuleCaches = mutableListOf<SideRuleCache>()

        fun resolve(
            rule: ProfileFieldLayoutRule.ToSideOf<*, *>,
            viewable: ProfileViewable,
            group: ItemsLayoutGroup
        ) {
            viewable.associatedField()?.let { field ->
                val isStart = rule.isStartField(field)
                val isEnd = rule.isEndField(field)

                // Get cached rule or create a new one.
                val cache = cache(rule)

                // Update rule cache.
                if (isStart) {
                    cache.startItem = viewable
                } else if (isEnd) {
                    cache.endItem = viewable
                }

                // Resolve rule if it is completely determined.
                executeResolve(cache, group)
            }
        }

        fun positions(): List<Int> = ArrayList(sidePositions)

        fun clear() {
            sidePositions.clear()
            sideRuleCaches.clear()
        }

        private fun cache(rule: ProfileFieldLayoutRule.ToSideOf<*, *>): SideRuleCache {
            return sideRuleCaches.firstOrNull {
                it.rule == rule
            } ?: {
                // Create and add an empty cache.
                val newCache = SideRuleCache(rule, null, null)
                sideRuleCaches.add(newCache)
                newCache
            }()
        }

        private fun executeResolve(cache: SideRuleCache, group: ItemsLayoutGroup) {
            ifNotNull(cache.startItem, cache.endItem) { startItem, endItem ->
                val firstElem =
                    group.items.firstOrNull { it.associatedField() == startItem.associatedField() }
                val secondElem =
                    group.items.firstOrNull { it.associatedField() == endItem.associatedField() }
                ifNotNull(firstElem, secondElem) { first, second ->
                    // If we got the elements, move the second one right after the first one.
                    val indexes = group.moveAfter(first, second)
                    if (indexes.first != -1 && indexes.second != -1) {
                        sidePositions.add(indexes.first)
                        sidePositions.add(indexes.second)
                    }
                }
            }
        }

        private class SideRuleCache(
            val rule: ProfileFieldLayoutRule.ToSideOf<*, *>,
            var startItem: ProfileViewable?,
            var endItem: ProfileViewable?
        )
    }

    private companion object {
        private const val GRID_MAX_SPANS = 2
    }
}