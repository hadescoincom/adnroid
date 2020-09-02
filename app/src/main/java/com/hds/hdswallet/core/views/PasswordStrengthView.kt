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
import androidx.constraintlayout.widget.ConstraintLayout
import com.hds.hdswallet.R
import kotlinx.android.synthetic.main.password_strength.view.*

/**
 *  10/23/18.
 */
class PasswordStrengthView : ConstraintLayout {
    var strength: Strength = Strength.EMPTY
        set(value) {
            field = value
            configLevel(strength)
        }

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
        inflate(context, R.layout.password_strength, this)

        if (attrs != null) {
            val a = context.theme.obtainStyledAttributes(
                    attrs,
                    R.styleable.PasswordStrengthView,
                    0, 0
            )

            strength = Strength.fromValue(a.getResourceId(R.styleable.PasswordStrengthView_strength, 0))
        }
    }

    private fun configLevel(strength: Strength) {
        veryWeak.setImageLevel(strength.value)
        weak.setImageLevel(
                when (strength) {
                    Strength.VERY_WEAK -> Strength.EMPTY.value
                    else -> strength.value
                })
        medium.setImageLevel(
                when (strength) {
                    Strength.VERY_WEAK, Strength.WEAK -> Strength.EMPTY.value
                    else -> strength.value
                })
        mediumStrong.setImageLevel(
                when (strength) {
                    Strength.VERY_WEAK, Strength.WEAK, Strength.MEDIUM -> Strength.EMPTY.value
                    else -> strength.value
                })
        strong.setImageLevel(
                when (strength) {
                    Strength.EMPTY, Strength.STRONG, Strength.VERY_STRONG -> strength.value
                    else -> Strength.EMPTY.value
                })
        veryStrong.setImageLevel(
                when (strength) {
                    Strength.EMPTY, Strength.VERY_STRONG -> strength.value
                    else -> Strength.EMPTY.value
                })
    }

    enum class Strength(val value: Int) {
        EMPTY(0), VERY_WEAK(1), WEAK(2), MEDIUM(3), MEDIUM_STRONG(4), STRONG(5), VERY_STRONG(6);

        companion object {
            private val map: HashMap<Int, Strength> = HashMap()

            init {
                values().forEach {
                    map[it.value] = it
                }
            }

            fun fromValue(type: Int): Strength {
                return map[type] ?: throw IllegalArgumentException("Unknown type of progress")
            }
        }
    }
}
