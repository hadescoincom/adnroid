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

import com.hds.hdswallet.base_screen.BasePresenter
import com.hds.hdswallet.core.App
import com.hds.hdswallet.core.OnboardManager

/**
 *  11/1/18.
 */
class WelcomeConfirmPresenter(currentView: WelcomeConfirmContract.View, currentRepository: WelcomeConfirmContract.Repository)
    : BasePresenter<WelcomeConfirmContract.View, WelcomeConfirmContract.Repository>(currentView, currentRepository),
        WelcomeConfirmContract.Presenter {

    override fun onCreate() {
        super.onCreate()
        repository.seed = view?.getData()
    }

    override fun onViewCreated() {
        super.onViewCreated()

        if (repository.seed != null) {
            view?.configSeed(repository.getSeedToValidate(), repository.seed!!)
            view?.initSuggestions(repository.getSuggestions())
        }
    }

    override fun onStart() {
        super.onStart()
        view?.showKeyboard()
    }

    override fun onSeedChanged(seed: String) {
        view?.handleNextButton()
        view?.updateSuggestions(seed)
    }

    override fun onSuggestionClick(text: String) {
        view?.setTextToCurrentView(text)
    }

    override fun onSeedFocusChanged(seed: String, hasFocus: Boolean) {
        view?.clearSuggestions()
        if (hasFocus) {
            view?.updateSuggestions(seed)
        }
    }


    override fun onNextPressed() {
        OnboardManager.instance.makeSecure()
        view?.showPasswordsFragment(repository.seed ?: return)
    }

    override fun onBackPressed() {
        if(App.isAuthenticated) {
            view?.showSeedFragment()
        }
        else{
            view?.showSeedAlert()
        }
    }

    override fun onCreateNewSeed() {
        view?.showSeedFragment()
    }
}
