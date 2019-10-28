package ru.test.wallpapersfrompixabay.utils

import android.content.Context

object Utility {
    private const val DEFAULT_COLUMNS_AMOUNT = 3

    fun calculateAmountOfColumns(
        context: Context?,
        columnWidthDp: Float
    ): Int {
        if (context == null) return DEFAULT_COLUMNS_AMOUNT

        val displayMetrics = context.resources.displayMetrics
        val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
        return (screenWidthDp / columnWidthDp + 0.5).toInt()
    }
}