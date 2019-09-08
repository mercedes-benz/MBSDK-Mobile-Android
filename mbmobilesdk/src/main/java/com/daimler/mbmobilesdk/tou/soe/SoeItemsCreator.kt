package com.daimler.mbmobilesdk.tou.soe

import android.content.Context
import android.util.SparseArray
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.utils.extensions.allAccepted
import com.daimler.mbmobilesdk.utils.extensions.newLine
import com.daimler.mbingresskit.common.SoeUserAgreement

/**
 * Creates SOE recycler items including links in the SOE descriptions.
 */
internal class SoeItemsCreator {

    fun createSoeItems(context: Context, agreements: List<SoeUserAgreement>): List<SoeRecyclerItem> {
        val result = mutableListOf<SoeRecyclerItem>()
        val mapped = SparseArray<MutableList<SoeUserAgreement>>()

        // Create one group per displayOrder.
        agreements.forEach {
            val curList = mapped[it.displayOrder] ?: mutableListOf()
            curList.add(it)
            mapped.put(it.displayOrder, curList)
        }

        // Create one title and one item per group.
        val size = mapped.size()
        repeat(size) { pos ->
            val currentOrder = mapped.keyAt(pos)
            mapped[currentOrder]?.let { agreements ->
                result.add(SoeTitleItem(getTitleForGroup(agreements, context.getString(R.string.agreement_title))))
                createSoeItemForAgreements(context, agreements)?.let { result.add(it) }
            }
        }

        return result
    }

    private fun getTitleForGroup(agreements: List<SoeUserAgreement>, fallback: String): String {
        return if (agreements.isNotEmpty()) {
            val title = agreements.firstOrNull { !it.titleText.isNullOrBlank() }?.titleText
            title ?: fallback
        } else {
            fallback
        }
    }

    private fun createSoeItemForAgreements(context: Context, agreements: List<SoeUserAgreement>): SoeAgreementItem? {
        return if (agreements.isNotEmpty()) {
            val description = createDescriptionAndLinks(agreements,
                context.getString(R.string.agreement_description_third_party))
            description?.let { desc ->
                val ids = agreements.map { it.documentId }
                val checked = isChecked(agreements)
                SoeAgreementItem(ids, checked, desc.description, desc.links)
            }
        } else {
            null
        }
    }

    private fun createDescriptionAndLinks(agreements: List<SoeUserAgreement>, fallbackDescription: String): SoeItemDescriptionResult? {
        return when (agreements.size) {
            0 -> null
            1 -> {
                val item = agreements.first()
                val description = getCheckboxText(item, fallbackDescription)
                val title = item.titleText
                if (!title.isNullOrBlank() && description.contains(title)) {
                    createDescriptionWithSingleLink(item, description, title)
                } else {
                    createDescriptionForMultiple(agreements, description) { it.fallbackDisplayName() }
                }
            }
            else -> {
                val description =
                    agreements.find {
                        !it.checkBoxText.isNullOrBlank()
                    }?.let {
                        getCheckboxText(it, fallbackDescription)
                    } ?: getCheckboxText(agreements.first(), fallbackDescription)
                createDescriptionForMultiple(agreements, description) { it.fallbackDisplayName() }
            }
        }
    }

    private fun getCheckboxText(agreement: SoeUserAgreement, fallbackText: String): String {
        val text = agreement.checkBoxText ?: fallbackText
        return if (agreement.generalUserAgreement) "$text *" else text
    }

    private fun createDescriptionWithSingleLink(
        agreement: SoeUserAgreement,
        mainText: String,
        linkText: String
    ): SoeItemDescriptionResult {
        return SoeItemDescriptionResult(
            mainText,
            listOf(SoeAgreementTextLink(agreement.documentId, linkText))
        )
    }

    private fun createDescriptionForMultiple(
        agreements: List<SoeUserAgreement>,
        mainText: String,
        fallbackName: (SoeUserAgreement) -> String
    ): SoeItemDescriptionResult {
        val text = StringBuilder(mainText).apply {
            newLine()
            agreements.forEach {
                append(it.displayName ?: fallbackName(it))
                newLine()
            }
        }.toString()
        val links = agreements.map {
            SoeAgreementTextLink(it.documentId, (it.displayName ?: fallbackName(it)))
        }
        return SoeItemDescriptionResult(text, links)
    }

    private fun isChecked(agreements: List<SoeUserAgreement>): Boolean {
        return agreements.allAccepted()
    }

    private fun SoeUserAgreement.fallbackDisplayName() = documentId

    private data class SoeItemDescriptionResult(val description: String, val links: List<SoeAgreementTextLink>)
}