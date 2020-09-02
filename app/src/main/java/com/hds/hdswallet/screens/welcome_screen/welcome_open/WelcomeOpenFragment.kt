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

import android.os.Bundle
import android.text.Editable
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.core.hardware.fingerprint.FingerprintManagerCompat
import androidx.core.os.CancellationSignal
import androidx.navigation.fragment.findNavController
import android.os.Handler
import androidx.activity.OnBackPressedCallback

import com.hds.hdswallet.R
import com.hds.hdswallet.base_screen.BaseFragment
import com.hds.hdswallet.base_screen.BasePresenter
import com.hds.hdswallet.base_screen.MvpRepository
import com.hds.hdswallet.base_screen.MvpView
import com.hds.hdswallet.core.App
import com.hds.hdswallet.core.helpers.FingerprintManager
import com.hds.hdswallet.core.helpers.WelcomeMode
import com.hds.hdswallet.core.watchers.TextWatcher

import com.hds.hdswallet.core.helpers.LockScreenManager
import com.hds.hdswallet.screens.app_activity.AppActivity
import com.hds.hdswallet.BuildConfig
import com.hds.hdswallet.core.views.Status
import com.hds.hdswallet.core.views.Type

import kotlinx.android.synthetic.main.fragment_welcome_open.*

/**
 *  10/19/18.
 */
class WelcomeOpenFragment : BaseFragment<WelcomeOpenPresenter>(), WelcomeOpenContract.View {
    private var cancellationSignal: CancellationSignal? = null
    private var authCallback: FingerprintCallback? = null
    private val passWatcher = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            presenter?.onPassChanged()
        }
    }

    override fun onControllerGetContentLayoutId() = R.layout.fragment_welcome_open
    override fun getToolbarTitle(): String? = ""

    private val onBackPressedCallback: OnBackPressedCallback = object: OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (LockScreenManager.isShowedLockScreen) {
            requireActivity().onBackPressedDispatcher.addCallback(activity!!, onBackPressedCallback)
        }

        appVersion.text = getString(R.string.version, BuildConfig.VERSION_NAME)
    }

    override fun onStart() {
        super.onStart()

        onBackPressedCallback.isEnabled = true
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

    override fun init(shouldInitBiometric: Boolean) {
        if (biometricView!=null)
        {
            if (!LockScreenManager.isShowedLockScreen) {
                App.isAuthenticated = false
            }
            else{
                btnChange.visibility = View.GONE
            }

            if (shouldInitBiometric) {
                if(presenter?.repository?.isFaceIDEnabled() == true) {
                    biometricView.setBiometricType(Type.FACE)
                    biometricView.visibility = View.VISIBLE
                    description.setText(R.string.welcome_open_description_with_faceid)
                    displayFaceIDPromt()
                }
                else if(presenter?.repository?.isFingerPrintEnabled() == true)
                {
                    cancellationSignal = CancellationSignal()

                    authCallback = FingerprintCallback(this, presenter, cancellationSignal)

                    biometricView.visibility = View.VISIBLE

                    FingerprintManagerCompat.from(App.self).authenticate(FingerprintManager.cryptoObject, 0, cancellationSignal,
                            authCallback!!, null)

                    description.setText(R.string.welcome_open_description_with_fingerprint)
                }
            }
            else {
                description.setText(R.string.enter_your_password_to_access_the_wallet)

                biometricView.visibility = View.GONE

                pass.requestFocus()

                Handler().postDelayed({
                    pass.requestFocus()
                    showKeyboard()
                }, 100)
            }

            passLayout.typeface = ResourcesCompat.getFont(context!!, R.font.roboto_regular)
        }
    }

    private fun displayFaceIDPromt() {
        App.self.showFaceIdPrompt(this,getString(R.string.use_faceid_access_wallet).replace(".","")) {
            when (it) {
                Status.SUCCESS -> {
                    biometricSuccess()
                    presenter?.onBiometricSucceeded()
                }
                Status.ERROR -> {
                    biometricError()
                    presenter?.onBiometricError()
                }
                Status.FAILED -> {
                    biometricFailed()
                    presenter?.onBiometricFailed()
                }
            }
        }
    }

    override fun addListeners() {
        val v = findViewById<com.hds.hdswallet.core.views.HdsButton>(R.id.btnOpen)

        if (btnOpen!=null)
        {
            btnOpen.setOnClickListener {
                presenter?.onOpenWallet()
            }

            btnChange.setOnClickListener {
                presenter?.onChangeWallet()
            }

            biometricView.setOnClickListener {
                if(presenter?.repository?.isFaceIDEnabled() == true) {
                    displayFaceIDPromt()
                }
            }

            pass.addTextChangedListener(passWatcher)
        }
    }

    override fun hasValidPass(): Boolean {
        var hasErrors = false
        passError.visibility = View.INVISIBLE
        pass.isStateAccent = true

        if (pass.text.isNullOrBlank()) {
            passError.text = getString(R.string.password_can_not_be_empty)
            passError.visibility = View.VISIBLE
            pass.isStateError = true
            hasErrors = true
        }

        return !hasErrors
    }

    fun biometricFailed() {
        biometricView.setStatus(Status.FAILED)
    }

    fun biometricError() {
        biometricView.setStatus(Status.ERROR)

        pass.requestFocus()

        showKeyboard()
    }

    fun biometricSuccess() {
        biometricView.setStatus(Status.SUCCESS)
    }

    override fun clearError() {
        passError.visibility = View.INVISIBLE
        pass.isStateAccent = true
    }

    override fun clearListeners() {
        btnOpen.setOnClickListener(null)
        btnChange.setOnClickListener(null)

        pass.removeTextChangedListener(passWatcher)
    }

    override fun clearFingerprintCallback() {
        authCallback?.clear()
        authCallback = null
        cancellationSignal?.cancel()
        cancellationSignal = null
    }

    override fun getPass(): String = pass.text?.toString() ?: ""

    override fun openWallet(pass: String) {
        hideKeyboard()
        if (LockScreenManager.isShowedLockScreen) {
            LockScreenManager.isShowedLockScreen = false

            (activity as? AppActivity)?.enableLeftMenu(true)

            LockScreenManager.restartTimer(activity!!.applicationContext)

            findNavController().popBackStack()
        }
        else{
            findNavController().navigate(WelcomeOpenFragmentDirections.actionWelcomeOpenFragmentToWelcomeProgressFragment(pass, WelcomeMode.OPEN.name, null))
        }
    }
    override fun changeWallet() {
        findNavController().navigate(WelcomeOpenFragmentDirections.actionWelcomeOpenFragmentToWelcomeCreateFragment())
    }

    override fun back() {
        findNavController().navigate(WelcomeOpenFragmentDirections.actionWelcomeOpenFragmentToWelcomeCreateFragment())
    }

    override fun showOpenWalletError() {
        pass.isStateError = true
        passError.text = getString(R.string.pass_wrong)
        passError.visibility = View.VISIBLE
    }

    override fun showChangeAlert() {
        showAlert(getString(R.string.welcome_change_alert),
                getString(R.string.welcome_btn_change_alert),
                { presenter?.onChangeConfirm() },
                getString(R.string.welcome_title_change_alert),
                getString(R.string.cancel))
    }

    override fun showBiometricAuthError() {
        if(biometricView.type == Type.FACE) {
            showToast(getString(R.string.common_faceid_error), 3000)
        }
        else{
            showToast(getString(R.string.common_fingerprint_error), 3000)
        }
    }

    override fun initPresenter(): BasePresenter<out MvpView, out MvpRepository> {
        return WelcomeOpenPresenter(this, WelcomeOpenRepository())
    }

    private class FingerprintCallback(var view: WelcomeOpenFragment?, var presenter: WelcomeOpenContract.Presenter?, var cancellationSignal: CancellationSignal?): FingerprintManagerCompat.AuthenticationCallback() {
        override fun onAuthenticationError(errMsgId: Int, errString: CharSequence?) {
            super.onAuthenticationError(errMsgId, errString)

            if(errMsgId != 5) {
                view?.biometricError()
                presenter?.onBiometricError()
            }

            cancellationSignal?.cancel()
        }

        override fun onAuthenticationSucceeded(result: FingerprintManagerCompat.AuthenticationResult?) {
            super.onAuthenticationSucceeded(result)
            view?.biometricSuccess()
            presenter?.onBiometricSucceeded()
            cancellationSignal?.cancel()
        }

        override fun onAuthenticationFailed() {
            super.onAuthenticationFailed()
            view?.biometricFailed()
            presenter?.onBiometricFailed()
        }

        fun clear() {
            view = null
            presenter = null
            cancellationSignal = null
        }
    }
}
