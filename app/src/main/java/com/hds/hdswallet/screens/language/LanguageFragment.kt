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

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.hds.hdswallet.R
import com.hds.hdswallet.base_screen.BaseFragment
import com.hds.hdswallet.base_screen.BasePresenter
import com.hds.hdswallet.base_screen.MvpRepository
import com.hds.hdswallet.base_screen.MvpView
import com.hds.hdswallet.core.App
import com.hds.hdswallet.core.helpers.LocaleHelper
import com.hds.hdswallet.screens.app_activity.AppActivity
import kotlinx.android.synthetic.main.fragment_language.*
import java.util.Locale
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController


class LanguageFragment: BaseFragment<LanguagePresenter>(), LanguageContract.View {
    private lateinit var adapter: LanguageAdapter

    override fun onControllerGetContentLayoutId(): Int = R.layout.fragment_language

    override fun getToolbarTitle(): String? = getString(R.string.language)
    override fun getStatusBarColor(): Int = if (App.isDarkMode) {
    ContextCompat.getColor(context!!, R.color.addresses_status_bar_color_black)
}
else{
    ContextCompat.getColor(context!!, R.color.addresses_status_bar_color)
}

    override fun init(languages: List<LocaleHelper.SupportedLanguage>, language: LocaleHelper.SupportedLanguage) {
        adapter = LanguageAdapter(languages) {
            presenter?.onSelectLanguage(it)
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter.setSelected(language)
    }

    override fun changeLanguage(language: LocaleHelper.SupportedLanguage) {
        adapter.setSelected(language)
        presenter?.onRestartPressed(language)

        val config = context!!.resources.configuration
        var locale: Locale = Locale(language.languageCode)
        Locale.setDefault(locale)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocale(locale)
        } else {
            config.locale = locale;
        }

        context!!.resources.updateConfiguration(config, context!!.resources.displayMetrics)

        (activity as? AppActivity)?.reloadMenu()

        findNavController().popBackStack()
    }

    override fun initPresenter(): BasePresenter<out MvpView, out MvpRepository> {
        return LanguagePresenter(this, LanguageRepository())
    }
}