package com.daimler.mbloggerkit.shake

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.daimler.mbloggerkit.Priority
import com.daimler.mbloggerkit.R
import com.daimler.mbloggerkit.export.LogEntry

class LogRecyclerAdapter(private val entries: List<LogEntry>, private val colorProvider: ColorProvider?) : RecyclerView.Adapter<LogRecyclerAdapter.LogViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogViewHolder {
        return LogViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_log, parent, false), colorProvider)
    }

    override fun getItemCount(): Int {
        return entries.size
    }

    override fun onBindViewHolder(holder: LogViewHolder, position: Int) {
        holder.update(entries[position])
    }

    class LogViewHolder(itemView: View, private val colorProvider: ColorProvider?) : RecyclerView.ViewHolder(itemView) {

        private val text = itemView.findViewById<TextView>(R.id.log_text)

        internal fun update(logEntry: LogEntry) {
            text.text = logEntry.message
            colorForPriority(logEntry.message)?.let {
                text.setTextColor(Color.parseColor(it))
            }
        }

        private fun colorForPriority(message: String): String? {
            return colorProvider?.let {
                Priority.values().firstOrNull {
                    message.contains(it.name)
                }?.let {
                    colorProvider.getColor(it)
                }
            }
        }
    }

    interface ColorProvider {
        fun getColor(priority: Priority): String
    }
}
