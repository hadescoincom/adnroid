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

package com.hds.hdswallet.screens.owner_key

import android.view.View
import androidx.navigation.fragment.findNavController
import com.hds.hdswallet.R
import com.hds.hdswallet.base_screen.BaseFragment
import com.hds.hdswallet.base_screen.BasePresenter
import com.hds.hdswallet.base_screen.MvpRepository
import com.hds.hdswallet.base_screen.MvpView
import com.hds.hdswallet.core.views.addDoubleDots
import kotlinx.android.synthetic.main.fragment_owner_key.*

class OwnerKeyFragment: BaseFragment<OwnerKeyPresenter>(), OwnerKeyContract.View {

    override fun getToolbarTitle(): String? = getString(R.string.show_owner_key)
    override fun onControllerGetContentLayoutId(): Int = R.layout.fragment_owner_key

    override fun init(key: String) {
        ownerKeyValue.text = key
        codeTitle.addDoubleDots()

        progressBar.visibility = View.GONE
        ownerKeyValue.visibility = View.VISIBLE
        ownerKeyDescription.visibility = View.VISIBLE
        btnCopy.visibility = View.VISIBLE
    }

    override fun addListeners() {
        btnCopy.setOnClickListener {
            presenter?.onCopyPressed()
            findNavController().popBackStack()
        }
    }

    override fun clearListeners() {
        btnCopy.setOnClickListener(null)
    }

    override fun showCopiedSnackBar() {
        showSnackBar(getString(R.string.owner_key_copied_message))
    }

    override fun initPresenter(): BasePresenter<out MvpView, out MvpRepository> {
        return OwnerKeyPresenter(this, OwnerKeyRepository(), OwnerKeyState())
    }

}