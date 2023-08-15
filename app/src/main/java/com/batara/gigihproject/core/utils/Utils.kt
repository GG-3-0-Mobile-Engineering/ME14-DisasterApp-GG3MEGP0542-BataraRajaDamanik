package com.batara.gigihproject.core.utils

import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Utils {
    fun encodeDate(startDate: Long, endDate: Long): List<String> {
        val startDate = startDate
        val endDate = endDate

        val startDateText = startDate?.let { Date(it).toString() } ?: "Not selected"
        val endDateText = endDate?.let { Date(it).toString() } ?: "Not selected"

        val inputFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US)
        val outputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US)

        val date = inputFormat.parse(startDateText)
        val formattedDate = date?.let { it1 -> outputFormat.format(it1) }

        val date2 = inputFormat.parse(endDateText)
        val formattedDate2 = date2?.let { it1 -> outputFormat.format(it1) }

        val encodedDate = URLEncoder.encode(formattedDate, "UTF-8")
        val encodedDate2 = URLEncoder.encode(formattedDate2, "UTF-8")

        return listOf(
            encodedDate,
            encodedDate2
        )
    }
}