/*
 * // Copyright 2018 Hds Development
 * //
 * // Licensed under the Apache License, Version 2.0 (the "License");
 * // you may not use this file except in compliance with the License.
 * // You may obtain a copy of the License at
 * //
 * //    http://www.apache.org/licenses/LICENSE-2.0
 * //
 * // Unless required by applicable law or agreed to in writing, software
 * // distributed under the License is distributed on an "AS IS" BASIS,
 * // WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * // See the License for the specific language governing permissions and
 * // limitations under the License.
 */

package com.hds.hdswallet.core.helpers

import com.hds.hdswallet.core.AppManager
import com.hds.hdswallet.core.entities.Currency
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

/**
 *  3/14/19.
 */
fun Long.convertToHdsString(): String = (this.toDouble() / 100000000).convertToHdsString()
fun Double.convertToHdsString(): String = DecimalFormat("#.########").apply { decimalFormatSymbols = DecimalFormatSymbols.getInstance(Locale.US) }.format(this)
fun Long.convertToHds(): Double = this.toDouble() / 100000000
fun Long.convertToHdsWithSign(isSent: Boolean) = if (isSent) "-${this.convertToHdsString()}" else "+${this.convertToHdsString()}"
fun Double.convertToGroth() = Math.round(this * 100000000)

fun Long.convertToCurrencyString(): String? {
    val current = AppManager.instance.currentExchangeRate()

    if (current?.currency == Currency.Off) {
        return null
    }

    if (current!=null && this != 0L) {
        val value = current.amount.toDouble() / 100000000
        val hds = this.convertToHds()
        val rate = value * hds
        if (current.currency == Currency.Usd) {
            return  DecimalFormat("#.##").apply { decimalFormatSymbols = DecimalFormatSymbols.getInstance(Locale.US) }.format(rate) + " USD"
        }
        else if (current.currency == Currency.Bitcoin) {
            return  DecimalFormat("#.########").apply { decimalFormatSymbols = DecimalFormatSymbols.getInstance(Locale.US) }.format(rate) + " BTC"
        }
    }
    else if (current==null || this == 0L) {
        if (AppManager.instance.currentCurrency() == Currency.Usd) {
            return  "-USD"
        }
        else if (AppManager.instance.currentCurrency() == Currency.Bitcoin) {
            return  "-BTC"
        }
    }


    return null
}

fun Long.convertToCurrencyGrothString(): String? {
    val current = AppManager.instance.currentExchangeRate()

    if (current?.currency == Currency.Off) {
        return null
    }

    if (current!=null && this != 0L) {
        val value = current.amount.toDouble() / 100000000
        val hds = this.convertToHds()
        var rate = value * hds
        if (current.currency == Currency.Usd) {
            if(rate < 0.01) {
                return "< 1 cent";
            }
            return  DecimalFormat("#.##").apply { decimalFormatSymbols = DecimalFormatSymbols.getInstance(Locale.US) }.format(rate) + " USD"
        }
        else if (current.currency == Currency.Bitcoin) {
            val resultString = DecimalFormat("#.########").apply { decimalFormatSymbols = DecimalFormatSymbols.getInstance(Locale.US) }.format(rate) + " BTC"
            if(resultString == "0 BTC") {
                rate *= 100000000;
                return DecimalFormat("#.########").apply { decimalFormatSymbols = DecimalFormatSymbols.getInstance(Locale.US) }.format(rate) + " satoshis"
            }

            return resultString
        }
    }
    else if (current==null || this == 0L) {
        if (AppManager.instance.currentCurrency() == Currency.Usd) {
            return  "-USD"
        }
        else if (AppManager.instance.currentCurrency() == Currency.Bitcoin) {
            return  "-BTC"
        }
    }

    return null
}


fun Double.convertToCurrencyString(): String? {
    val current = AppManager.instance.currentExchangeRate()

    if (current?.currency == Currency.Off) {
        return null
    }

    if (current!=null && this != 0.0) {
        val value = current.amount.toDouble() / 100000000
        val rate = value * this
        if (current.currency == Currency.Usd) {
            return  DecimalFormat("#.##").apply { decimalFormatSymbols = DecimalFormatSymbols.getInstance(Locale.US) }.format(rate) + " USD"
        }
        else if (current.currency == Currency.Bitcoin) {
            return  DecimalFormat("#.########").apply { decimalFormatSymbols = DecimalFormatSymbols.getInstance(Locale.US) }.format(rate) + " BTC"
        }
    }
    else if (current==null || this == 0.0) {
        if (AppManager.instance.currentCurrency() == Currency.Usd) {
            return  "-USD"
        }
        else if (AppManager.instance.currentCurrency() == Currency.Bitcoin) {
            return  "-BTC"
        }
    }

    return null
}
