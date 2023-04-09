package com.zahand0.cowboys.presentation.ui.util

import java.text.NumberFormat
import java.util.Currency

object CurrencyUtil {
    val currencyFormat: NumberFormat = NumberFormat.getCurrencyInstance().apply {
        maximumFractionDigits = 0
        currency = Currency.getInstance("RUB")
    }
}