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

import com.hds.hdswallet.base_screen.BasePresenter
import com.hds.hdswallet.core.App
import com.hds.hdswallet.core.AppConfig
import java.io.File
import com.hds.hdswallet.core.helpers.*

/**
 *  10/19/18.
 */
class WelcomeOpenPresenter(currentView: WelcomeOpenContract.View, currentRepository: WelcomeOpenContract.Repository)
    : BasePresenter<WelcomeOpenContract.View, WelcomeOpenContract.Repository>(currentView, currentRepository),
        WelcomeOpenContract.Presenter {

    private val VIBRATION_LENGTH: Long = 100
    private var isOpenedWallet = false
    private var isRestore = false

    override fun onStart() {
        super.onStart()

        if (PreferencesManager.getBoolean(PreferencesManager.KEY_UNFINISHED_RESTORE)) {

            PreferencesManager.putString(PreferencesManager.KEY_NODE_ADDRESS,"")
            PreferencesManager.putBoolean(PreferencesManager.KEY_CONNECT_TO_RANDOM_NODE,true)
            PreferencesManager.putBoolean(PreferencesManager.KEY_UNFINISHED_RESTORE,false)

            removeDatabase()
        }

        if (isRecoverDataBaseExists()) {
            repository?.closeWallet()
            checkRecoverDataBase()
            repository?.isWalletInitialized()
        }


        view?.init(repository.isFingerPrintEnabled() || repository.isFaceIDEnabled())

        isRestore = false
    }

    override fun onOpenWallet() {
        view?.hideKeyboard()

        if (view?.hasValidPass() == true) {
            if (LockScreenManager.isShowedLockScreen) {
                if (repository.checkPass(view?.getPass())) {
                    view?.openWallet(view?.getPass() ?: return)
                }
                else{
                    view?.showOpenWalletError()
                }
            }
            else{
                openWallet(view?.getPass())
            }
        }
    }

    override fun onPassChanged() {
        view?.clearError()
    }

    override fun onChangeWallet() {
        view?.clearError()
        view?.showChangeAlert()
    }

    override fun onChangeConfirm() {
        val oldFile = File(AppConfig.DB_PATH, AppConfig.DB_FILE_NAME)
        val recoverFile = File(AppConfig.DB_PATH, AppConfig.DB_FILE_NAME_RECOVER)

        if (oldFile.exists()) {
            oldFile.copyTo(recoverFile,true)
        }

        val journalOldFile = File(AppConfig.DB_PATH, AppConfig.NODE_JOURNAL_FILE_NAME)
        val journalRecoverFile = File(AppConfig.DB_PATH, AppConfig.NODE_JOURNAL_FILE_NAME_RECOVER)

        if (journalOldFile.exists()) {
            journalOldFile.copyTo(journalRecoverFile,true)
        }

        val oldTags = PreferencesManager.getString(PreferencesManager.KEY_TAG_DATA)
        if(oldTags != null) {
            PreferencesManager.putString(PreferencesManager.KEY_TAG_DATA_RECOVER, oldTags)
            PreferencesManager.putString(PreferencesManager.KEY_TAG_DATA, "")
        }

        App.self.stopBackgroundService()

        isRestore = true

        view?.changeWallet()
    }

    override fun onBiometricError() {
        if (!isOpenedWallet && !isRestore) {
            view?.showBiometricAuthError()
        }
    }

    override fun onBiometricSucceeded() {
        if (LockScreenManager.isShowedLockScreen) {
            if (repository.checkPass(PreferencesManager.getString(PreferencesManager.KEY_PASSWORD))) {
                view?.openWallet(view?.getPass() ?: return)
            }
            else{
                view?.showOpenWalletError()
            }
        }
        else{
            openWallet(PreferencesManager.getString(PreferencesManager.KEY_PASSWORD))
        }
    }

    override fun onBiometricFailed() {
        view?.vibrate(VIBRATION_LENGTH)
    }

    override fun onStop() {
        view?.clearFingerprintCallback()
        super.onStop()
    }

    override fun hasBackArrow(): Boolean? = false

    private fun openWallet(pass: String?) {
        if (Status.STATUS_OK == repository.openWallet(pass)) {
            isOpenedWallet = true
            view?.openWallet(view?.getPass() ?: return)
        } else {
            view?.showOpenWalletError()
        }
    }
}
