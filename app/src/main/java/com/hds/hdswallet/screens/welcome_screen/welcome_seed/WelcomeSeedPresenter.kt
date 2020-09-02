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

package com.hds.hdswallet.screens.welcome_screen.welcome_seed

import com.hds.hdswallet.BuildConfig
import com.hds.hdswallet.base_screen.BasePresenter
import com.hds.hdswallet.core.App
import com.hds.hdswallet.core.AppConfig
import com.hds.hdswallet.core.OnboardManager
import com.hds.hdswallet.core.helpers.PreferencesManager
import com.hds.hdswallet.core.utils.LogUtils

/**
 *  10/30/18.
 */
class WelcomeSeedPresenter(currentView: WelcomeSeedContract.View, currentRepository: WelcomeSeedContract.Repository)
    : BasePresenter<WelcomeSeedContract.View, WelcomeSeedContract.Repository>(currentView, currentRepository),
        WelcomeSeedContract.Presenter {
    private val COPY_TAG = "RECOVERY SEED"

    private var seed = mutableListOf<String>()

    override fun onViewCreated() {
        super.onViewCreated()

        seed = repository.seed().toMutableList()

        view?.configSeed(seed.toTypedArray())
    }

    override fun onDonePressed() {
        view?.showConfirmFragment(seed.toTypedArray())
    }

    override fun onNextPressed() {
        if (!App.isAuthenticated) {
            PreferencesManager.putBoolean(PreferencesManager.KEY_SEED_IS_SKIP, false)
        }

        view?.showSaveAlert()
    }

    override fun onCopyPressed() {
        view?.copyToClipboard(prepareSeed(seed.toTypedArray()), COPY_TAG)
        view?.showCopiedAlert()
    }

    override fun oLaterPressed() {
        if (!App.isAuthenticated) {
            PreferencesManager.putBoolean(PreferencesManager.KEY_SEED_IS_SKIP, true)
            view?.showPasswordFragment(seed.toTypedArray())
        }
        else{
            view?.onBack()
        }
    }

    private fun prepareSeed(seed: Array<String>): String {
        val result = StringBuilder()

        for ((index, value) in seed.withIndex()) {
            result.append((index + 1).toString())
                    .append(" ")
                    .append(value)

            if (index != seed.lastIndex) {
                result.append("\n")
            }
        }

        return result.toString()
    }
}
