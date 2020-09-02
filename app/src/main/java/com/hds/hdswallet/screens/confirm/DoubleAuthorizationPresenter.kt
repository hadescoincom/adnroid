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

package com.hds.hdswallet.screens.confirm

import com.hds.hdswallet.base_screen.BasePresenter
import com.hds.hdswallet.core.helpers.FaceIDManager
import com.hds.hdswallet.core.helpers.FingerprintManager
import com.hds.hdswallet.core.helpers.PreferencesManager

class DoubleAuthorizationPresenter(view: DoubleAuthorizationContract.View?, repository: DoubleAuthorizationContract.Repository)
    : BasePresenter<DoubleAuthorizationContract.View, DoubleAuthorizationContract.Repository>(view, repository), DoubleAuthorizationContract.Presenter {

    override fun onViewCreated() {
        super.onViewCreated()
        view?.init(isEnableFingerprint() || isEnableFaceID())
    }

    override fun onFingerprintSuccess() {
        view?.displayFingerPrint(false)
    }

    override fun onError() {
        view?.error()
    }

    override fun onFailed() {
        view?.showFailed()
    }

    override fun onSuccess() {
        view?.success()
    }

    override fun onNext() {
        val password = view?.getPassword()

        when {
            password.isNullOrBlank() -> view?.showEmptyPasswordError()
            repository.checkPassword(password) -> {
                view?.navigateToNextScreen()
            }
            else -> view?.showWrongPasswordError()
        }
    }

    fun isEnableFingerprint(): Boolean {
        return PreferencesManager.getBoolean(PreferencesManager.KEY_IS_FINGERPRINT_ENABLED)
                && FingerprintManager.isManagerAvailable()
    }

    fun isEnableFaceID(): Boolean {
        return PreferencesManager.getBoolean(PreferencesManager.KEY_IS_FINGERPRINT_ENABLED)
                && FaceIDManager.isManagerAvailable()
    }

    override fun onChangePassword() {
        view?.clearPasswordError()
    }

    override fun onStop() {
        view?.clearFingerprintCallback()
        super.onStop()
    }
}