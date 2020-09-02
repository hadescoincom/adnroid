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

package com.hds.hdswallet.screens.create_password

import com.hds.hdswallet.base_screen.MvpPresenter
import com.hds.hdswallet.base_screen.MvpRepository
import com.hds.hdswallet.base_screen.MvpView
import com.hds.hdswallet.core.helpers.Status
import com.hds.hdswallet.core.helpers.WelcomeMode
import com.hds.hdswallet.core.views.PasswordStrengthView

/**
 *  10/23/18.
 */
interface PasswordContract {
    interface View : MvpView {
        fun hasErrors(): Boolean
        fun setStrengthLevel(strength: PasswordStrengthView.Strength)
        fun clearErrors()
        fun getPass(): String
        fun getSeed(): Array<String>?
        fun getWelcomeMode(): WelcomeMode?
        fun isModeChangePass(): Boolean
        fun proceedToWallet(mode : WelcomeMode, pass: String, seed: Array<String>)
        fun showRestoreModeChoice(pass: String, seed: Array<String>)
        fun showSeedAlert()
        fun showSeedFragment()
        fun showOldPassError()
        fun init(isModeChangePass: Boolean, mode: WelcomeMode)
        fun completePassChanging()
    }

    interface Presenter : MvpPresenter<View> {
        fun onPassChanged(pass: String?)
        fun onConfirmPassChanged()
        fun onProceed()
        fun onBackPressed()
        fun onCreateNewSeed()
    }

    interface Repository : MvpRepository {
        fun createWallet(pass: String?, phrases: String?, mode : WelcomeMode): Status
        fun checkPass(pass: String?): Boolean
        fun changePass(pass: String?)
    }
}
