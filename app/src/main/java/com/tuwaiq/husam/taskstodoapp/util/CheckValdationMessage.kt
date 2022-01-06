package com.tuwaiq.husam.taskstodoapp.util

import android.content.Context
import com.tuwaiq.husam.taskstodoapp.R

fun getInvalidMessage(message: String, context: Context): String =
    when {
        message == "Can't be empty!" -> context.getString(R.string.invalid_not_empty)
        message == "Invalid Email Address!" -> context.getString(R.string.invalid_email)
        message == "At least one letter should be in upper case." -> context.getString(R.string.invalid_upper_case)
        message == "At least one letter should be a number." -> context.getString(R.string.invalid_one_number)
        message.contains("Length should be greater than ") -> context.getString(R.string.invalid_greater_than)+" "+ message.substring(
            message.length - 2
        )
        message.contains("Length should be less than or equal to ") -> context.getString(R.string.invalid_less_than)+" " + message.substring(
            message.length - 2
        )
        message == "Should not contain any numbers!" -> context.getString(R.string.invalid_number)
        message == "Should not contain any special characters" -> context.getString(R.string.invalid_special)
        message == "Should not contain any alphabet characters!" -> context.getString(R.string.invalid_alphabet)
        else -> "Error"
    }
