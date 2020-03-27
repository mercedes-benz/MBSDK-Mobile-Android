package com.daimler.mbloggerkit.shake

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbloggerkit.R
import com.daimler.mbloggerkit.export.LogEntry
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class LogDialogFragment : BottomSheetDialogFragment() {

    private var colorProvider: LogRecyclerAdapter.ColorProvider? = null

    companion object {

        internal const val EXTRA_ORDER = "extra.order"

        fun show(fragmentManager: FragmentManager, order: LogOverlay.Order, colorProvider: LogRecyclerAdapter.ColorProvider = MaterialColorProvider()) {
            val fragment = LogDialogFragment()
            val bundle = Bundle().apply {
                putInt(EXTRA_ORDER, order.ordinal)
            }
            fragment.arguments = bundle
            fragment.colorProvider = colorProvider
            fragment.show(fragmentManager, null)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_log, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val entries = orderedEntries(
            LogOverlay.Order.values().getOrElse(
                arguments?.getInt(EXTRA_ORDER)
                    ?: LogOverlay.Order.CHRONOLOGICAL_DESCENDING.ordinal
            ) {
                LogOverlay.Order.CHRONOLOGICAL_DESCENDING
            }
        )
        val logRecycler = view.findViewById<RecyclerView>(R.id.log_entries)
        logRecycler.layoutManager = LinearLayoutManager(context)
        logRecycler.adapter = LogRecyclerAdapter(entries, colorProvider)
    }

    private fun orderedEntries(order: LogOverlay.Order): List<LogEntry> {
        val entries = MBLoggerKit.loadCurrentLog().entries()
        return if (order == LogOverlay.Order.CHRONOLOGICAL_DESCENDING) {
            entries.reversed()
        } else {
            entries
        }
    }
}
