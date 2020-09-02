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

package com.hds.hdswallet.screens.language

import com.hds.hdswallet.base_screen.MvpPresenter
import com.hds.hdswallet.base_screen.MvpRepository
import com.hds.hdswallet.base_screen.MvpView
import com.hds.hdswallet.core.helpers.LocaleHelper

interface LanguageContract {

    interface View: MvpView {
        fun init(languages: List<LocaleHelper.SupportedLanguage>, language: LocaleHelper.SupportedLanguage)
        fun changeLanguage(language: LocaleHelper.SupportedLanguage)
    }

    interface Presenter: MvpPresenter<View> {
        fun onSelectLanguage(language: LocaleHelper.SupportedLanguage)
        fun onRestartPressed(language: LocaleHelper.SupportedLanguage)
    }

    interface Repository: MvpRepository {
        fun getLanguages(): List<LocaleHelper.SupportedLanguage>
        fun getCurrentLanguage(): LocaleHelper.SupportedLanguage
        fun setLanguage(language: LocaleHelper.SupportedLanguage)
    }
}