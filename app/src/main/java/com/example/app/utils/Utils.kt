package com.example.app.utils

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan

fun String?.asMoney(
    isPrefix: Boolean = false,
    isBold: Boolean = false,
    addRub: Boolean = true
): Spanned {
    if (this == null) return SpannableString("?")
    var s = this


    val ruble = "\u20BD"

    return if (addRub)
        if (isPrefix) {
            s = "$ruble $s"

            val spannable =
                SpannableString(s)

            spannable.setSpan(RelativeSizeSpan(0.8f), 0, 2, 0)
            if (isBold)
                spannable.setSpan(
                    StyleSpan(Typeface.BOLD),
                    2,
                    spannable.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )


            spannable
        } else {
            s = "$s $ruble"
            val spannable = SpannableString(s)
            spannable.setSpan(RelativeSizeSpan(0.8f), s.length - 1, s.length, 0)
            if (isBold)
                spannable.setSpan(
                    StyleSpan(Typeface.BOLD),
                    0,
                    s.length - 1,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            spannable
        }
    else
        SpannableString(s)
}


fun convertAmountToRouble(amountPenny: Long): String {

    val sb = when (amountPenny.toString().length) {
        1 -> StringBuilder("00${amountPenny}")
        2 -> StringBuilder("0${amountPenny}")
        else -> StringBuilder(amountPenny.toString())
    }

    sb.insert(sb.length - 2, ".")
    return sb.toString()
}

