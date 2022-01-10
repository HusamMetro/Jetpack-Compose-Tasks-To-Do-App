package com.tuwaiq.husam.taskstodoapp.components

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.*

fun showDatePicker(
    activity: AppCompatActivity,
    updatedDate: (Long?) -> Unit
) {
    val picker = MaterialDatePicker.Builder.datePicker().build()
    picker.show(activity.supportFragmentManager, picker.toString())
    picker.addOnPositiveButtonClickListener {
        updatedDate(it)
    }
}

fun dateFormatter(milliseconds: Long?): String? {
    milliseconds?.let {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = it
        return formatter.format(calendar.time)
    }
    return null
}

fun dateFormatterForMock(milliseconds: Long?): String? {
    milliseconds?.let {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = it
        calendar.add(Calendar.DATE, 7)
        return formatter.format(calendar.time)
    }
    return null
}
