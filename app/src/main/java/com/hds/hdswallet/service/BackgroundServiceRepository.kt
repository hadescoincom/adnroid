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

package com.hds.hdswallet.service

import com.hds.hdswallet.base_screen.BaseRepository
import com.hds.hdswallet.core.Api
import com.hds.hdswallet.core.entities.OnTxStatusData
import com.hds.hdswallet.core.helpers.PreferencesManager
import com.hds.hdswallet.core.listeners.WalletListener
import io.reactivex.Observable
import io.reactivex.subjects.Subject

class BackgroundServiceRepository: BaseRepository() {
    fun isWalletRunning(): Boolean {
        return Api.isWalletRunning()
    }

    fun getPassword(): String? {
        return PreferencesManager.getString(PreferencesManager.KEY_PASSWORD)
    }

    fun getTxStatus(): Observable<OnTxStatusData> {
        return getResult(WalletListener.obsOnTxStatus, "getTxStatus") { wallet?.getWalletStatus() }
    }
}