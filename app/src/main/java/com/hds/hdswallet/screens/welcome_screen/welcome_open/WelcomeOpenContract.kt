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

package com.hds.hdswallet.screens.welcome_screen.welcome_open

import com.hds.hdswallet.base_screen.MvpPresenter
import com.hds.hdswallet.base_screen.MvpRepository
import com.hds.hdswallet.base_screen.MvpView

/**
 *  10/19/18.
 */
interface WelcomeOpenContract {
    interface View : MvpView {
        fun init(shouldInitBiometric : Boolean)
        fun hasValidPass(): Boolean
        fun getPass(): String
        fun openWallet(pass: String)
        fun changeWallet()
        fun showChangeAlert()
        fun showOpenWalletError()
        fun showBiometricAuthError()
        fun clearError()
        fun clearFingerprintCallback()
        fun back()
    }

    interface Presenter : MvpPresenter<View> {
        fun onOpenWallet()
        fun onChangeWallet()
        fun onChangeConfirm()
        fun onPassChanged()
        fun onBiometricError()
        fun onBiometricSucceeded()
        fun onBiometricFailed()
    }

    interface Repository : MvpRepository {
        fun isFingerPrintEnabled(): Boolean
        fun isFaceIDEnabled(): Boolean
        fun checkPass(pass: String?): Boolean
    }
}
