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

package com.hds.hdswallet.screens.change_address

import android.text.Editable
import android.text.TextWatcher
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hds.hdswallet.R
import com.hds.hdswallet.base_screen.BaseFragment
import com.hds.hdswallet.base_screen.BasePresenter
import com.hds.hdswallet.base_screen.MvpRepository
import com.hds.hdswallet.base_screen.MvpView
import com.hds.hdswallet.core.entities.WalletAddress
import com.hds.hdswallet.screens.addresses.AddressesAdapter
import kotlinx.android.synthetic.main.fragment_change_address.*
import android.graphics.Typeface

class ChangeAddressFragment : BaseFragment<ChangeAddressPresenter>(), ChangeAddressContract.View {

    companion object {
        var callback: ChangeAddressCallback? = null
    }

    private lateinit var adapter: AddressesAdapter

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {

            if (searchAddress.text.toString().isEmpty())
            {
                searchAddress.setTypeface(null,Typeface.ITALIC)
            }
            else{
                searchAddress.setTypeface(null,Typeface.NORMAL)
            }

            presenter?.onChangeSearchText(s?.toString() ?: "")
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    override fun isFromReceive(): Boolean = ChangeAddressFragmentArgs.fromBundle(arguments!!).isFromReceive

    override fun getGeneratedAddress(): WalletAddress? = ChangeAddressFragmentArgs.fromBundle(arguments!!).generatedAddress

    override fun getToolbarTitle(): String? = getString(R.string.change_address_title)

    override fun onControllerGetContentLayoutId(): Int = R.layout.fragment_change_address

    override fun getSearchText(): String = searchAddress.text?.toString() ?: ""

    override fun init(state: ChangeAddressContract.ViewState, generatedAddress: WalletAddress?) {
        gradientView.setBackgroundResource(if (state == ChangeAddressContract.ViewState.Receive) R.drawable.receive_toolbar_gradient else R.drawable.send_toolbar_gradient)

        searchAddress.setTypeface(null,Typeface.ITALIC)

        if (state == ChangeAddressContract.ViewState.Send) {
            tokenTitle.text = getString(R.string.outgoing_address)
        }

        adapter = AddressesAdapter(context!!, object: AddressesAdapter.OnItemClickListener {
            override fun onItemClick(item: WalletAddress) {
                presenter?.onItemPressed(item)
            }
        }, null,
                { presenter?.onSearchTagsForAddress(it) ?: listOf() }, generatedAddress)

        addressesRecyclerView.layoutManager = LinearLayoutManager(context)
        addressesRecyclerView.adapter = adapter
    }

    override fun getStatusBarColor(): Int {
        return ContextCompat.getColor(context!!, if (isFromReceive()) R.color.received_color else R.color.sent_color)
    }

    override fun addListeners() {
        searchAddress.addTextChangedListener(textWatcher)
    }

    override fun clearListeners() {
        searchAddress.removeTextChangedListener(textWatcher)
    }

    override fun updateList(items: List<WalletAddress>) {
        adapter.setData(items)
    }

    override fun back(walletAddress: WalletAddress?) {
        if (walletAddress != null) {
            callback?.onChangeAddress(walletAddress)
        }

        findNavController().popBackStack()
    }

    override fun setAddress(address: String) {
        searchAddress.setText(address)
    }

    override fun showNotHdsAddressError() {
        showSnackBar(getString(R.string.send_error_not_hds_address))
    }


    override fun initPresenter(): BasePresenter<out MvpView, out MvpRepository> {
        return ChangeAddressPresenter(this, ChangeAddressRepository(), ChangeAddressState())
    }
}