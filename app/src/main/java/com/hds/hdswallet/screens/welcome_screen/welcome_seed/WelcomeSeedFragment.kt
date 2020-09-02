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

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.GridLayout
import androidx.navigation.fragment.findNavController
import com.hds.hdswallet.BuildConfig
import com.hds.hdswallet.R
import com.hds.hdswallet.base_screen.BaseFragment
import com.hds.hdswallet.base_screen.BasePresenter
import com.hds.hdswallet.base_screen.MvpRepository
import com.hds.hdswallet.base_screen.MvpView
import com.hds.hdswallet.core.App
import com.hds.hdswallet.core.AppConfig
import com.hds.hdswallet.core.helpers.WelcomeMode
import com.hds.hdswallet.core.utils.LogUtils
import com.hds.hdswallet.core.views.HdsPhrase
import kotlinx.android.synthetic.main.fragment_welcome_seed.*


/**
 *  10/30/18.
 */
class WelcomeSeedFragment : BaseFragment<WelcomeSeedPresenter>(), WelcomeSeedContract.View {

    private fun onlyDisplay():Boolean {
        return WelcomeSeedFragmentArgs.fromBundle(arguments!!).onlyDisplay
    }

    private lateinit var copiedAlert: String

    override fun onControllerGetContentLayoutId() = R.layout.fragment_welcome_seed
    override fun getToolbarTitle(): String? = getString(R.string.welcome_seed_title)

    override fun onControllerCreate(extras: Bundle?) {
        super.onControllerCreate(extras)

        copiedAlert = getString(R.string.copied)
    }

    override fun addListeners() {
        forbidScreenshot()

        btnNext.setOnClickListener {
            presenter?.onNextPressed()
        }

        if (BuildConfig.FLAVOR != AppConfig.FLAVOR_MAINNET)
        {
            seedLayout.setOnLongClickListener {
                presenter?.onCopyPressed()
                return@setOnLongClickListener true
            }
        }

        btnLater.setOnClickListener {
            presenter?.oLaterPressed()
        }


        if(onlyDisplay())
        {
            description.text = getString(R.string.welcome_seed_description_old)
            btnLater.visibility = View.GONE
            btnNext.visibility = View.GONE
        }
        else if(App.isAuthenticated) {
            btnLater.visibility = View.GONE
        }

    }

    override fun clearListeners() {
        allowScreenshot()

        btnNext.setOnClickListener(null)
        btnLater.setOnClickListener(null)
        btnLater.setOnLongClickListener(null)
    }

    private fun forbidScreenshot() {
        activity?.window?.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)
    }

    private fun allowScreenshot() {
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
    }

    override fun configSeed(seed: Array<String>) {
        if (BuildConfig.DEBUG) {
            LogUtils.log("Seed phrase: \n$seed")
        }

        val sideOffset: Int = resources.getDimensionPixelSize(R.dimen.welcome_grid_element_side_offset)
        val topOffset: Int = resources.getDimensionPixelSize(R.dimen.welcome_grid_element_top_offset)
        var columnIndex = 0
        var rowIndex = 0
        seedLayout.rowCount = seed.size / 2

        for ((index, value) in seed.withIndex()) {
            if (columnIndex == seedLayout.columnCount) {
                columnIndex = 0
                rowIndex++
            }

            seedLayout.addView(configPhrase(value, index + 1, rowIndex, columnIndex, sideOffset, topOffset))
            columnIndex++
        }
    }

    override fun showCopiedAlert() {
        showSnackBar(copiedAlert)
    }

    override fun showSaveAlert() {
        showAlert(getString(R.string.welcome_seed_save_description),
                getString(R.string.done),
                { presenter?.onDonePressed() },
                getString(R.string.welcome_seed_save_title),
                getString(R.string.cancel))
    }

    override fun showConfirmFragment(seed: Array<String>) {
        findNavController().navigate(WelcomeSeedFragmentDirections.actionWelcomeSeedFragmentToWelcomeConfirmFragment(seed))
    }

    override fun showPasswordFragment(seed: Array<String>) {
        findNavController().navigate(WelcomeSeedFragmentDirections.actionWelcomeSeedFragmentToPasswordFragment(seed, WelcomeMode.CREATE.name))
    }

    override fun onBack() {
        findNavController().popBackStack()
    }

    private fun configPhrase(text: String, number: Int, rowIndex: Int, columnIndex: Int, sideOffset: Int, topOffset: Int): View? {
        val context = context ?: return null

        val phrase = HdsPhrase(context)
        phrase.phrase = text
        phrase.number = number

        val params = GridLayout.LayoutParams()
        params.height = GridLayout.LayoutParams.WRAP_CONTENT
        params.width = GridLayout.LayoutParams.WRAP_CONTENT
        params.columnSpec = GridLayout.spec(columnIndex, 1f)
        params.rowSpec = GridLayout.spec(rowIndex)
        params.topMargin = topOffset

        when (columnIndex) {
            0 -> params.rightMargin = sideOffset
            1 -> params.leftMargin = sideOffset
        }

        phrase.layoutParams = params

        return phrase
    }

    override fun initPresenter(): BasePresenter<out MvpView, out MvpRepository> {
        return WelcomeSeedPresenter(this, WelcomeSeedRepository())
    }
}
