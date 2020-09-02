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
package com.hds.hdswallet.base_screen

import com.hds.hdswallet.core.Api
import com.hds.hdswallet.core.App
import com.hds.hdswallet.core.AppConfig
import com.hds.hdswallet.core.AppManager
import com.hds.hdswallet.core.entities.Wallet
import com.hds.hdswallet.core.helpers.LockScreenManager
import com.hds.hdswallet.core.helpers.PreferencesManager
import com.hds.hdswallet.core.helpers.Status
import com.hds.hdswallet.core.utils.LogUtils
import io.reactivex.Observable
import io.reactivex.subjects.Subject

/**
 *  10/1/18.
 */
open class BaseRepository : MvpRepository {

    override val wallet: Wallet?
        get() = AppManager.instance.wallet


    override fun isPrivacyModeEnabled() = PreferencesManager.getBoolean(PreferencesManager.KEY_PRIVACY_MODE)

    override fun setPrivacyModeEnabled(isEnabled: Boolean) {
        PreferencesManager.putBoolean(PreferencesManager.KEY_PRIVACY_MODE, isEnabled)
        if (isEnabled) {
            PreferencesManager.putBoolean(PreferencesManager.KEY_PRIVACY_MODE_NEED_CONFIRM, false)
        }
    }

    override fun openWallet(pass: String?): Status {
        var result = Status.STATUS_ERROR

        if (!pass.isNullOrBlank()) {
            val nodeAddress = PreferencesManager.getString(PreferencesManager.KEY_NODE_ADDRESS)
            if (!isEnabledConnectToRandomNode() && !nodeAddress.isNullOrBlank()) {
                AppConfig.NODE_ADDRESS = nodeAddress
            } else {
                AppConfig.NODE_ADDRESS = Api.getDefaultPeers().random()
            }

            if (PreferencesManager.getString(PreferencesManager.KEY_PASSWORD) != pass) {
                LogUtils.logResponse(result, "openWallet")
                return result
            }

            if (!Api.isWalletRunning()) {
                try {
                    AppManager.instance.wallet = Api.openWallet(AppConfig.APP_VERSION, AppConfig.NODE_ADDRESS, AppConfig.DB_PATH, pass)
                }
                catch(e:Exception) {
                    return  Status.STATUS_ERROR
                }

                if (wallet != null) {
                    PreferencesManager.putString(PreferencesManager.KEY_PASSWORD, pass)
                    result = Status.STATUS_OK
                }
            }
            else if (AppManager.instance.wallet?.checkWalletPassword(pass) == true) {
                result = Status.STATUS_OK
            }
        }

        LogUtils.logResponse(result, "openWallet")
        return result
    }

    override fun isEnabledConnectToRandomNode(): Boolean {
        return PreferencesManager.getBoolean(PreferencesManager.KEY_CONNECT_TO_RANDOM_NODE, true)
    }

    override fun closeWallet() {
        if (Api.isWalletRunning()) {
            getResult("closeWallet") {
                Api.closeWallet()
                AppManager.instance.wallet = null
                AppManager.instance.unSubscribeToUpdates()
            }
        }
    }

    override fun isWalletInitialized(): Boolean {
        val result = Api.isWalletInitialized(AppConfig.DB_PATH)
        LogUtils.logResponse(result, "isWalletInitialized")
        return result
    }

    override fun isLockScreenEnabled(): Boolean {
        return LockScreenManager.getCurrentValue() != LockScreenManager.LOCK_SCREEN_NEVER_VALUE
    }

    fun <T> getResult(subject: Subject<T>, requestName: String, additionalInfo: String = "", block: () -> Unit = {}): Subject<T> {
        LogUtils.log(StringBuilder()
                .append(LogUtils.LOG_REQUEST)
                .append(" ")
                .append(requestName)
                .append("\n")
                .append(additionalInfo)
                .append("\n")
                .append("--------------------------")
                .append("\n").toString())
        block.invoke()
        return subject
    }

    fun <T> getResult(subject: Observable<T>, requestName: String, additionalInfo: String = "", block: () -> Unit = {}): Observable<T> {
        LogUtils.log(StringBuilder()
                .append(LogUtils.LOG_REQUEST)
                .append(" ")
                .append(requestName)
                .append("\n")
                .append(additionalInfo)
                .append("\n")
                .append("--------------------------")
                .append("\n").toString())
        block.invoke()
        return subject
    }

    fun <T> getResult(requestName: String, additionalInfo: String = "", block: () -> T): T {
        LogUtils.log(StringBuilder()
                .append(LogUtils.LOG_REQUEST)
                .append(" ")
                .append(requestName)
                .append("\n")
                .append(additionalInfo)
                .append("\n")
                .append("--------------------------")
                .append("\n").toString())
        return block.invoke()
    }
}
