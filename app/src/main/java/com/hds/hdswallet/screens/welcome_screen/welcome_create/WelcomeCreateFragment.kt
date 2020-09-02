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

package com.hds.hdswallet.screens.welcome_screen.welcome_create

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.hds.hdswallet.BuildConfig
import com.hds.hdswallet.R
import com.hds.hdswallet.base_screen.BaseFragment
import com.hds.hdswallet.base_screen.BasePresenter
import com.hds.hdswallet.base_screen.MvpRepository
import com.hds.hdswallet.base_screen.MvpView
import com.hds.hdswallet.core.helpers.LocaleHelper
import kotlinx.android.synthetic.main.fragment_welcome_create.*
/**
 *  12/4/18.
 */
class WelcomeCreateFragment : BaseFragment<WelcomeCreatePresenter>(), WelcomeCreateContract.View {
    override fun onControllerGetContentLayoutId() = R.layout.fragment_welcome_create
    override fun getToolbarTitle(): String? = ""
    override fun hasBackArrow(): Boolean = WelcomeCreateFragmentArgs.fromBundle(arguments!!).hasBackArrow

    private val onBackPressedCallback: OnBackPressedCallback = object: OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            presenter?.onBackPressed()
        }
    }

    override fun addListeners() {
        btnCreate.setOnClickListener {
            presenter?.onCreateWallet()
        }

        btnRestore.setOnClickListener {
            presenter?.onRestoreWallet()
        }

        btnBack.setOnClickListener {
            if (onBackPressedCallback.isEnabled) {
                onBackPressedCallback.handleOnBackPressed()
            }
        }

        btnChangeLanguage.setOnClickListener {
            presenter?.onChangeLanguagePressed()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(activity!!, onBackPressedCallback)
        appVersion.text = getString(R.string.version, BuildConfig.VERSION_NAME)
    }

    override fun onStart() {
        super.onStart()

        onBackPressedCallback.isEnabled = true

        btnBack.visibility = if (presenter?.hasBackArrow() == true) View.VISIBLE else View.GONE
    }

    override fun setupLanguageButton(currentLanguage: LocaleHelper.SupportedLanguage) {
        btnChangeLanguage.visibility = View.GONE
        btnChangeLanguage.text = currentLanguage.languageCode.toUpperCase()
    }

    override fun navigateToLanguageSettings() {
        findNavController().navigate(WelcomeCreateFragmentDirections.actionWelcomeCreateFragmentToLanguageFragment())
    }

    override fun onStop() {
        onBackPressedCallback.isEnabled = false
        super.onStop()
    }

    override fun onDestroy() {
        onBackPressedCallback.isEnabled = false
        onBackPressedCallback.remove()
        super.onDestroy()
    }

    override fun back() {
        findNavController().popBackStack()
    }

    override fun finish() {
        activity?.finish()
    }

    override fun clearListeners() {
        btnCreate.setOnClickListener(null)
        btnRestore.setOnClickListener(null)
        btnBack.setOnClickListener(null)
        btnChangeLanguage.setOnClickListener(null)
    }

    override fun createWallet() {
        findNavController().navigate(WelcomeCreateFragmentDirections.actionWelcomeCreateFragmentToWelcomeDescriptionFragment())
    }
    override fun restoreWallet() {
        findNavController().navigate(WelcomeCreateFragmentDirections.actionWelcomeCreateFragmentToWelcomeRestoreFragment())
    }

    override fun initPresenter(): BasePresenter<out MvpView, out MvpRepository> {
        return WelcomeCreatePresenter(this, WelcomeCreateRepository())
    }
}

