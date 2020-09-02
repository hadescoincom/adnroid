package com.hds.hdswallet.core.entities

import android.content.Context
import android.os.Parcelable
import com.hds.hdswallet.R
import com.hds.hdswallet.core.entities.dto.ExchangeRateDTO
import kotlinx.android.parcel.Parcelize


enum class Currency(val value: Int) {
    Hds(0),
    Bitcoin(1),
    Litecoin(3),
    Usd(4),
    Off(-1);

    companion object {
        private val map: HashMap<Int, Currency> = HashMap()

        init {
            values().forEach {
                map[it.value] = it
            }
        }

        fun fromValue(type: Int): Currency {
            return map[type] ?: Usd
        }
    }

    fun name(context:Context): String {
        return when {
            this == Bitcoin -> {
                context.getString(R.string.btc)
            }
            this == Off -> {
                context.getString(R.string.off)
            }
            else -> {
                context.getString(R.string.usd)
            }
        }
    }
}

@Parcelize
data class ExchangeRate(private val source: ExchangeRateDTO) : Parcelable {
    var currency:Currency = Currency.fromValue(source.unit)
    var unit = source.unit
    var amount = source.amount
}