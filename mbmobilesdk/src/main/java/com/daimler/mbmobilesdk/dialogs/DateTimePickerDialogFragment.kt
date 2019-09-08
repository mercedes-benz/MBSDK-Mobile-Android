package com.daimler.mbmobilesdk.dialogs

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.TextView
import android.widget.TimePicker
import androidx.constraintlayout.widget.ConstraintLayout
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.utils.extensions.toLocalTime24String
import com.daimler.mbmobilesdk.utils.extensions.toOnlyDate
import com.daimler.mbuikit.components.dialogfragments.MBGenericDialogFragment
import com.daimler.mbuikit.components.dialogfragments.buttons.ClickablePurpose
import com.daimler.mbuikit.components.dialogfragments.buttons.DialogButtonOrientation
import com.daimler.mbuikit.components.dialogfragments.buttons.DialogClickable
import com.daimler.mbuikit.components.viewmodels.MBGenericDialogViewModel
import java.util.*

internal class DateTimePickerDialogFragment(val callback: SelectDateCallback, private val preselectedDate: Date? = null) : MBGenericDialogFragment<MBGenericDialogViewModel>() {

    private var calendar: Calendar = Calendar.getInstance()
    private var datePickerVisible = false
    private var timePickerVisible = true

    // Views
    private var timePickerHeader: ConstraintLayout? = null
    private var timePickerCurrent: TextView? = null
    private var timePicker: TimePicker? = null
    private var timePickerArrow: ImageView? = null

    private var datePickerHeader: ConstraintLayout? = null
    private var datePickerCurrent: TextView? = null
    private var datePicker: DatePicker? = null
    private var datePickerArrow: ImageView? = null

    override fun onNegativeAction() = callback.onCancelSelectDateDialog()

    override fun onPositiveAction() = callback.onPickDateTime(calendar.time)

    override fun <T> onButtonClicked(button: DialogClickable<T>) {
        when (button.purpose) {
            ClickablePurpose.NEGATIVE -> callback.onCancelSelectDateDialog()
            ClickablePurpose.POSITIVE -> callback.onPickDateTime(calendar.time)
        }
    }

    override fun onInflateBelowMessage(root: ViewGroup) {
        super.onInflateBelowMessage(root)
        if (preselectedDate != null) {
            calendar.time = preselectedDate
        }

        activity ?: return
        val layout = LayoutInflater.from(activity!!.applicationContext).inflate(R.layout.dialog_support_datetimepicker, null)
        root.addView(layout)

        // TimePicker find Views
        timePickerHeader = root.findViewById<ConstraintLayout>(R.id.mb_container_timepicker_header)
        timePickerArrow = root.findViewById<ImageView>(R.id.arrow_clock_icon)
        timePickerCurrent = root.findViewById<TextView>(R.id.clock_currenttime)
        timePicker = root.findViewById<TimePicker>(R.id.mb_time_picker)

        // DatePicker find Views
        datePickerArrow = root.findViewById<ImageView>(R.id.arrow_date_icon)
        datePickerHeader = root.findViewById<ConstraintLayout>(R.id.mb_container_datepicker_header)
        datePickerCurrent = root.findViewById<TextView>(R.id.calender_currentdate)
        datePicker = root.findViewById<DatePicker>(R.id.mb_date_picker)

        // Set initial values
        toggleIcons(timePickerArrow!!, true)
        toggleIcons(datePickerArrow!!, false)
        timePicker!!.setIs24HourView(true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker!!.hour = calendar.get(Calendar.HOUR_OF_DAY)
            timePicker!!.minute = calendar.get(Calendar.MINUTE)
            timePickerCurrent!!.text = calendar.time.toLocalTime24String()
        }
        datePickerCurrent!!.text = calendar.time.toOnlyDate()
        datePicker!!.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)) { _, year, monthOfYear, dayOfMonth ->
            calendar.set(year, monthOfYear, dayOfMonth)
            datePickerCurrent!!.text = calendar.time.toOnlyDate()
        }
        datePicker!!.maxDate = Date().time

        setListeners()
    }

    private fun setListeners() {
        timePickerHeader!!.setOnClickListener {
            timePickerVisible = !timePickerVisible
            toggleTimePickers()
        }

        timePicker!!.setOnTimeChangedListener { _, hourOfDay, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calendar.set(Calendar.MINUTE, minute)
            timePickerCurrent!!.text = calendar.time.toLocalTime24String()
        }

        datePickerHeader!!.setOnClickListener {
            datePickerVisible = !datePickerVisible
            toggleDatePicker()
        }
    }

    private fun toggleTimePickers() {
        if (datePicker == null || timePicker == null) return
        if (datePickerVisible && timePickerVisible) {
            datePicker!!.visibility = View.GONE
            toggleIcons(datePickerArrow!!, false)
            timePicker!!.visibility = View.VISIBLE
            toggleIcons(timePickerArrow!!, true)
            return
        }
        if (!datePickerVisible && timePickerVisible) {
            timePicker!!.visibility = View.VISIBLE
            toggleIcons(timePickerArrow!!, true)
            return
        }
        timePicker!!.visibility = View.GONE
        toggleIcons(timePickerArrow!!, false)
    }

    private fun toggleDatePicker() {
        if (datePicker == null || timePicker == null) return
        if (datePickerVisible && timePickerVisible) {
            datePicker!!.visibility = View.VISIBLE
            toggleIcons(datePickerArrow!!, true)
            timePicker!!.visibility = View.GONE
            toggleIcons(timePickerArrow!!, false)
            return
        }
        if (datePickerVisible && !timePickerVisible) {
            datePicker!!.visibility = View.VISIBLE
            toggleIcons(datePickerArrow!!, true)
            return
        }
        datePicker!!.visibility = View.GONE
        toggleIcons(datePickerArrow!!, false)
    }

    private fun toggleIcons(view: ImageView, expanded: Boolean) {
        val drawableId = if (expanded) R.drawable.ic_arrow_drop_up else R.drawable.ic_arrow_drop_down
        view.setImageResource(drawableId)
    }

    interface SelectDateCallback {
        fun onPickDateTime(selection: Date)
        fun onCancelSelectDateDialog()
    }

    companion object {

        fun newInstance(
            callback: SelectDateCallback,
            preselectedDate: Date?,
            id: Int,
            title: String?,
            message: String?,
            positiveButtonText: String?,
            negativeButtonText: String?
        ): DateTimePickerDialogFragment =
                DateTimePickerDialogFragment(callback, preselectedDate).apply {
                    putBundle(id, title, message, positiveButtonText, negativeButtonText, DialogButtonOrientation.HORIZONTAL)
                }
    }
}
