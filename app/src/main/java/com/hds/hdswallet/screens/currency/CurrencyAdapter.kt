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

package com.hds.hdswallet.screens.currency

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hds.hdswallet.R
import com.hds.hdswallet.core.App
import com.hds.hdswallet.core.entities.Currency
import com.hds.hdswallet.core.entities.ExchangeRate
import com.hds.hdswallet.screens.app_activity.AppActivity

class CurrencyAdapter(private val currencies: List<ExchangeRate>, private val onSelected: (Currency) -> Unit): RecyclerView.Adapter<CurrencyAdapter.ViewHolder>() {
    private var selectedCurrency: Currency = Currency.Usd

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_currency, parent, false))
    }

    override fun getItemCount(): Int = currencies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currency = currencies[position]

        holder.selected = selectedCurrency.value == currency.currency.value
        holder.name = currency.currency.name(App.self)
        holder.itemView.setOnClickListener { onSelected(currency.currency) }
    }

    fun currency(language: Currency) {
        selectedCurrency = language
        notifyDataSetChanged()
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var selected: Boolean = false
            set(value) {
                field = value
                val visibility = if (field) View.VISIBLE else View.GONE
                itemView.findViewById<ImageView>(R.id.selectedImage).visibility = visibility
            }

        var name: String = ""
            set(value) {
                field = value
                itemView.findViewById<TextView>(R.id.name).text = field
            }
    }
}