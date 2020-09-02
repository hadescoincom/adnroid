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

package com.hds.hdswallet.screens.welcome_screen.welcome_confirm

import com.hds.hdswallet.base_screen.MvpPresenter
import com.hds.hdswallet.base_screen.MvpRepository
import com.hds.hdswallet.base_screen.MvpView

/**
 *  11/1/18.
 */
interface WelcomeConfirmContract {
    interface View : MvpView {
        fun getData(): Array<String>?
        fun configSeed(seedToValidate: List<Int>, seed : Array<String>)
        fun showPasswordsFragment(seed : Array<String>)
        fun handleNextButton()
        fun initSuggestions(suggestions: List<String>)
        fun setTextToCurrentView(text: String)
        fun updateSuggestions(text: String)
        fun clearSuggestions()
        fun showSeedAlert()
        fun showSeedFragment()
    }

    interface Presenter : MvpPresenter<View> {
        fun onNextPressed()
        fun onSeedChanged(seed: String)
        fun onBackPressed()
        fun onCreateNewSeed()
        fun onSuggestionClick(text: String)
        fun onSeedFocusChanged(seed: String, hasFocus: Boolean)
    }

    interface Repository : MvpRepository {
        fun getSeedToValidate(): List<Int>
        fun getSuggestions(): List<String>
        var seed : Array<String>?
    }
}
