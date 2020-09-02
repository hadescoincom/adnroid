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
import android.text.Editable
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.hds.hdswallet.R
import com.hds.hdswallet.core.watchers.TextWatcher
import kotlinx.android.synthetic.main.common_phrase_input.view.*

/**
 *  11/1/18.
 */
class HdsPhraseInput : ConstraintLayout {
    var phrase: String = ""
    var isForEnsure: Boolean = false
    val isEmpty: Boolean
        get() = editText.text?.toString()?.isEmpty() ?: true
    val isValid: Boolean
        get() = phrase == editText.text?.toString()?.trim() || validator?.invoke(editText.text?.toString()?.trim()) ?: false
    var number: Int = Int.MIN_VALUE
        set(value) {
            field = value
            if (field != Int.MIN_VALUE) {
                numberView.text = field.toString()
            }
        }
    var numberTextColorResId: Int = Integer.MIN_VALUE
        set(value) {
            field = value
            if (field != Integer.MIN_VALUE) {
                numberView.setTextColor(ContextCompat.getColor(context, field))
            }
        }
    var numberBackgroundResId: Int = Integer.MIN_VALUE
        set(value) {
            field = value
            if (field != Integer.MIN_VALUE) {
                numberView.background = ContextCompat.getDrawable(context, field)
            }
        }

    val editText: HdsEditText by lazy {
        findViewWithTag("input") as HdsEditText
    }

    var validator: ((String?) -> Boolean)? = null

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
        inflate(context, R.layout.common_phrase_input, this)

        if (attrs != null) {
            val a = context.theme.obtainStyledAttributes(
                    attrs,
                    R.styleable.HdsPhraseInput,
                    0, 0
            )

            phrase = a.getNonResourceString(R.styleable.HdsPhrase_phrase)
            number = a.getResourceId(R.styleable.HdsPhraseInput_number, Integer.MIN_VALUE)
            isForEnsure = a.getBoolean(R.styleable.HdsPhraseInput_isForEnsure, false)
            numberTextColorResId = a.getResourceId(R.styleable.HdsPhraseInput_number_text_color, Integer.MIN_VALUE)
            numberBackgroundResId = a.getResourceId(R.styleable.HdsPhraseInput_number_background, Integer.MIN_VALUE)
        }

        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (isForEnsure) {
                    when {
                        isEmpty -> {
                            numberTextColorResId = R.color.common_text_color
                            numberBackgroundResId = R.drawable.empty_number_background
                            editText.isStateNormal = true
                        }
                        isValid -> {
                            numberTextColorResId = R.color.phrase_validated_number_text_color
                            numberBackgroundResId = R.drawable.validated_number_background
                            editText.isStateAccent = true
                        }
                        else -> {
                            numberTextColorResId = R.color.phrase_error_number_text_color
                            numberBackgroundResId = R.drawable.error_number_background
                            editText.isStateError = true
                        }
                    }
                } else {
                    if (editText.text?.toString()?.trim().isNullOrBlank()) {
                        numberTextColorResId = R.color.phrase_number_text_color
                        numberBackgroundResId = R.drawable.empty_number_background
                        editText.isStateNormal = true
                    } else {
                        numberTextColorResId = R.color.phrase_number_text_color
                        numberBackgroundResId = R.drawable.number_background
                        editText.isStateNormal = true
                    }
                }
            }
        })


        //necessary to prevent HdsEditText from changing text color
        editText.onFocusChangeListener = null
    }
}
