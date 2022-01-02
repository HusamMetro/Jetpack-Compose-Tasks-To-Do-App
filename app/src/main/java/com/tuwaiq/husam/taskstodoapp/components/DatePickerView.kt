package com.tuwaiq.husam.taskstodoapp.components

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.*

@ExperimentalComposeUiApi
@Composable
fun DatePickerview(
    datePicked : String?,
    updatedDate : ( date : Long?) -> Unit,
) {
    val activity = LocalContext.current as AppCompatActivity
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.TopStart)
            .padding(top = 10.dp)
            .border(0.5.dp, MaterialTheme.colors.onSurface.copy(alpha = 0.5f))
            .clickable {
                showDatePicker(activity, updatedDate)
            }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val (lable, iconView) = createRefs()
            Text(
                text= datePicked?:"Date Picker",
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier
//                    .fillMaxWidth()
                    /*.constrainAs(lable) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(iconView.start)
                        width = Dimension.fillToConstraints
                    }*/
            )

            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp, 20.dp)
                    /*.constrainAs(iconView) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }*/,
                tint = MaterialTheme.colors.onSurface
            )

        }
    }
}

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
fun DateFormater(milliseconds : Long?) : String? {
    milliseconds?.let{
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        val calendar: Calendar = Calendar.getInstance()
        calendar.setTimeInMillis(it)
        return formatter.format(calendar.getTime())
    }
    return null
}
/*

@ExperimentalComposeUiApi
@Preview(showBackground = true)
@Composable
fun Testing() {
    DatePickerview()
}*/
