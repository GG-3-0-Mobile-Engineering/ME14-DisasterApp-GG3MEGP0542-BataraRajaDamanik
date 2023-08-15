package com.batara.gigihproject.core.utils

import org.junit.Assert.*

import org.junit.Test
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class UtilsTest {

    @Test
    fun `encodeDate encodes correctly`() {
        val startDate = 1660483200000L // Example start date in milliseconds (2022-08-14)
        val endDate = 1660569600000L // Example end date in milliseconds (2022-08-15)

        val expectedStartDate = "2022-08-14T20%3A20%3A00%2B0700"
        val expectedEndDate = "2022-08-15T20%3A20%3A00%2B0700"

        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US)
        val outputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US)

        val startDateFormatted = inputFormat.format(Date(startDate))
        val endDateFormatted = inputFormat.format(Date(endDate))

        val encodedStartDate = URLEncoder.encode(outputFormat.format(inputFormat.parse(startDateFormatted)), "UTF-8")
        val encodedEndDate = URLEncoder.encode(outputFormat.format(inputFormat.parse(endDateFormatted)), "UTF-8")

        // Act
        val result = Utils.encodeDate(startDate, endDate)

        // Assert
        assertEquals(expectedStartDate, result[0])
        assertEquals(expectedEndDate, result[1])
    }
}