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

package com.hds.hdswallet.screens.wallet

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.hds.hdswallet.R
import com.hds.hdswallet.core.App
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_navigation.*

/**
 *  2/22/19.
 */
class NavItemsAdapter(private val context: Context, var data: Array<NavItem>, private var clickListener: OnItemClickListener) : androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {
    lateinit var selectedItem:NavItem.ID

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder =
            ViewHolder(layoutInflater.inflate(R.layout.item_navigation, parent, false))

    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
        val item = data[position]

        val selectedColor = ContextCompat.getColor(context, R.color.colorAccent)
        val unselectedColor = if (App.isDarkMode) {
            ContextCompat.getColor(context!!, R.color.ic_menu_color_dark)
        } else{
            ContextCompat.getColor(context!!, R.color.ic_menu_color)
        }

        (holder as ViewHolder).apply {
            icon.setImageDrawable(ContextCompat.getDrawable(context, item.iconResId))
            accentView.visibility = if (item.isSelected) View.VISIBLE else View.INVISIBLE
            title.text = item.text
            icon.setColorFilter(if (item.isSelected) selectedColor else unselectedColor)
            title.setTextColor(if (item.isSelected) selectedColor else unselectedColor)
            unreadTextView.visibility = if (item.unreadCount != 0) View.VISIBLE else View.GONE
            unreadTextView.text = item.unreadCount.toString()
            icon.alpha = if (item.isSelected) 1F else 0.8F
            title.alpha = if (item.isSelected) 1F else 0.8F

            itemView.setOnClickListener {
                clickListener.onItemClick(item)
            }
        }
    }

    fun selectItem(id: NavItem.ID) {
        selectedItem = id

        data.forEach {
            it.isSelected = id == it.id
        }

        notifyDataSetChanged()
    }

    override fun getItemCount() = data.size

    interface OnItemClickListener {
        fun onItemClick(navItem: NavItem)
    }

    class ViewHolder(override val containerView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(containerView), LayoutContainer
}
