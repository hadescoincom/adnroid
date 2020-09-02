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

package com.hds.hdswallet.core.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import com.hds.hdswallet.R
import androidx.asynclayoutinflater.view.AsyncLayoutInflater

/**
 *  12/10/18.
 */
class HdsToolbar : LinearLayout {
    var hasStatus: Boolean = false
        set(value) {
            field = value
            statusLayout.visibility = if (field) View.VISIBLE else View.GONE
        }
    var centerTitle: Boolean = false
        set(value) {
            field = value
            centerTitleView.visibility = if (field) View.VISIBLE else View.GONE
        }
    lateinit var toolbar: Toolbar
    lateinit var status: TextView
    lateinit var statusIcon: ImageView
    lateinit var progressBar: ProgressBar
    lateinit var centerTitleView: TextView
    private lateinit var statusLayout: ConstraintLayout

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
//        val asyncLayoutInflater = AsyncLayoutInflater(context!!)
//        asyncLayoutInflater.inflate( R.layout.toolbar, this) { view, _, parent ->
//            addView(view)
//        }

        inflate(context, R.layout.toolbar, this)
        toolbar = this.findViewById(R.id.toolbar)
        status = this.findViewById(R.id.connectionStatus)
        statusIcon = this.findViewById(R.id.statusIcon)
        statusLayout = this.findViewById(R.id.statusLayout)
        progressBar = this.findViewById(R.id.progress)
        centerTitleView = this.findViewById(R.id.centerTitle)

        this.orientation = VERTICAL

        if (attrs != null) {
            val a = context.theme.obtainStyledAttributes(
                    attrs,
                    R.styleable.HdsToolbar,
                    0, 0
            )

            hasStatus = a.getBoolean(R.styleable.HdsToolbar_hasStatus, false)
            centerTitle = a.getBoolean(R.styleable.HdsToolbar_centerTitle, false)
        }

        status.text = status.text.toString().toLowerCase()

        toolbar.setNavigationIcon(R.drawable.ic_back)
    }
}
