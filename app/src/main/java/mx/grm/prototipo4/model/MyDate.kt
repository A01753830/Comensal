package mx.grm.prototipo4.model

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

/**
 * Retrieving Date
 *
 * This utility class provides a method to retrieve the current date in a specific format.
 * It is useful for obtaining the date in "yyyy-MM-dd" format for various purposes in the application.
 *
 * @authors Héctor González Sánchez
 * @authors Alfredo Azamar López
 */

class MyDate() {
    /**
     * Retrieves the current date in "yyyy-MM-dd" format.
     *
     * @return A string representing the current date in the "yyyy-MM-dd" format.
     */
    fun getCurrentDate(): String {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Mexico_City"))
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }
}