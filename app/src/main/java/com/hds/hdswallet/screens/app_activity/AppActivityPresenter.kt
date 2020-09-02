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

package com.hds.hdswallet.screens.app_activity

import com.hds.hdswallet.base_screen.BasePresenter
import com.hds.hdswallet.core.App
import com.hds.hdswallet.core.helpers.PreferencesManager
import com.hds.hdswallet.core.helpers.removeDatabase
import android.util.Log

class AppActivityPresenter(view: AppActivityContract.View?, repository: AppActivityContract.Repository) : BasePresenter<AppActivityContract.View, AppActivityContract.Repository>(view, repository), AppActivityContract.Presenter {

    override fun onViewCreated() {
        super.onViewCreated()

        if (PreferencesManager.getBoolean(PreferencesManager.KEY_UNFINISHED_RESTORE)) {
            PreferencesManager.putString(PreferencesManager.KEY_NODE_ADDRESS,"")
            PreferencesManager.putBoolean(PreferencesManager.KEY_CONNECT_TO_RANDOM_NODE,true)
            removeDatabase()
        }
        else{
            if (repository.isWalletInitialized() && !App.isAuthenticated) {
                view?.showOpenFragment()
            }
            else if (App.isAuthenticated) {
                view?.showWalletFragment()
            }
        }
    }

    override fun onNewIntent(txId: String?) {
        if (!PreferencesManager.getBoolean(PreferencesManager.KEY_UNFINISHED_RESTORE)
                && repository.isWalletInitialized()) {
            if (App.isAuthenticated) {
                if (txId != null) {
                    view?.showTransactionDetailsFragment(txId)
                }
            }
        }
    }

    override fun onPendingSend(info: PendingSendInfo) {
        view?.startNewSnackbar({ repository.cancelSendMoney(info.token) }, { repository.sendMoney(info.outgoingAddress, info.token, info.comment, info.amount, info.fee) })
    }
}