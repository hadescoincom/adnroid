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

import com.hds.hdswallet.base_screen.BaseRepository
import com.hds.hdswallet.core.helpers.FingerprintManager
import com.hds.hdswallet.core.helpers.PreferencesManager
import com.hds.hdswallet.core.helpers.FaceIDManager


/**
 *  10/19/18.
 */
class WelcomeOpenRepository : BaseRepository(), WelcomeOpenContract.Repository {

    override fun isFingerPrintEnabled(): Boolean {
        return getResult("isFingerPrintEnabled") {
            PreferencesManager.getBoolean(PreferencesManager.KEY_IS_FINGERPRINT_ENABLED) && FingerprintManager.isManagerAvailable()
        }
    }

    override fun isFaceIDEnabled(): Boolean {
        return getResult("isFaceIDEnabled") {
            PreferencesManager.getBoolean(PreferencesManager.KEY_IS_FINGERPRINT_ENABLED) && FaceIDManager.isManagerAvailable()
        }
    }

    override fun checkPass(pass: String?): Boolean {
        return wallet?.checkWalletPassword(pass ?: return false) ?: false
    }
}
