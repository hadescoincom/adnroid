package com.hds.hdswallet.core.views

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.hds.hdswallet.R
import com.hds.hdswallet.core.App
import com.hds.hdswallet.core.helpers.Tag
import com.hds.hdswallet.core.helpers.TagHelper
import kotlinx.android.synthetic.main.item_selectable_tag.view.*

class TagAdapter(private val onSelectedChangeListener: (List<Tag>) -> Unit) : RecyclerView.Adapter<TagAdapter.ViewHolder>() {
    private val noneTag = Tag("none")

    private val allTags: List<Tag> = ArrayList(TagHelper.getAllTagsSorted(TagHelper.getAllTags())).apply {
        add(0, noneTag)
    }

    private var selectedTags: HashMap<String, Tag> = HashMap()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_selectable_tag, parent, false))
    }

    override fun getItemCount(): Int = allTags.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tag = allTags[position]

        holder.apply {
            if (tag.id != noneTag.id) {
                circleColorId = tag.color.getAndroidColorId()
                name = tag.name
                isSelected = selectedTags.containsKey(tag.id)

                setOnChangeSelectedListener {
                    if (it) {
                        selectedTags[tag.id] = tag
                    } else {
                        selectedTags.remove(tag.id)
                    }

                    onSelectedChangeListener.invoke(selectedTags.values.toList())

                    tryUpdateDataChanged()
                }
            } else {
                circleColorId = null
                name = itemView.context.getString(R.string.none)
                isSelected = selectedTags.isEmpty()

                setOnChangeSelectedListener {
                    if (it && selectedTags.isNotEmpty()) {
                        selectedTags.clear()
                        onSelectedChangeListener.invoke(selectedTags.values.toList())
                    }

                    tryUpdateDataChanged()
                }
            }
        }
    }

    private fun tryUpdateDataChanged() {
        try {
            notifyDataSetChanged()
        } catch (e: Exception) { }
    }

    fun setSelectedTags(tags: List<Tag>) {
        selectedTags = HashMap(tags.associateBy { it.id })
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        var circleColorId: Int? = 0
            set(value) {
                field = value

                val view = itemView.findViewById<View>(R.id.colorCircle)
                if (value != null) {
                    view.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(App.self, value))
                } else {
                    view.backgroundTintList = null
                }
            }

        var name: String = ""
            set(value) {
                field = value
                itemView.findViewById<TextView>(R.id.categoryName).text = field
            }

        var isSelected: Boolean = false
            set(value) {
                field = value
                itemView.findViewById<CheckBox>(R.id.tagCheckbox).isChecked = field
            }

        fun setOnChangeSelectedListener(function: (Boolean) -> Unit) {
            val checkbox = itemView.findViewById<CheckBox>(R.id.tagCheckbox)
            itemView.setOnClickListener { checkbox.isChecked = !checkbox.isChecked; function.invoke(checkbox.isChecked) }
            checkbox.setOnClickListener { function.invoke(checkbox.isChecked) }
        }

    }
}