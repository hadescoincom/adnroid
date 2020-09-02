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

package com.hds.hdswallet.screens.addresses

import com.hds.hdswallet.base_screen.MvpPresenter
import com.hds.hdswallet.base_screen.MvpRepository
import com.hds.hdswallet.base_screen.MvpView
import com.hds.hdswallet.core.entities.OnAddressesData
import com.hds.hdswallet.core.entities.OnTxStatusData
import com.hds.hdswallet.core.entities.TxDescription
import com.hds.hdswallet.core.entities.WalletAddress
import com.hds.hdswallet.core.helpers.Tag
import com.hds.hdswallet.core.helpers.TrashManager
import io.reactivex.Observable
import io.reactivex.subjects.Subject

/**
 *  2/28/19.
 */
interface AddressesContract {
    interface View : MvpView {
        fun init()
        fun updateAddresses(tab: Tab, addresses: List<WalletAddress>)
        fun updatePlaceholder(showPlaceholder:Boolean)
        fun showAddressDetails(address: WalletAddress)
        fun navigateToAddContactScreen()
        fun navigateToEditAddressScreen()
        fun copyAddress()
        fun deleteAddresses()
        fun showDeleteAddressesDialog(transactionAlert:Boolean)
        fun changeMode(mode: AddressesFragment.Mode)
        fun didSelectAllAddresses(addresses: List<WalletAddress>)
        fun didUnSelectAllAddresses()
    }

    interface Presenter : MvpPresenter<View> {
        fun onAddressPressed(address: WalletAddress)
        fun onSearchTagsForAddress(address: String): List<Tag>
        fun onAddContactPressed()
        fun onEditAddressPressed()
        fun onCopyAddressPressed()
        fun onDeleteAddressesPressed()
        fun onDeleteAddress(selected: List<String>)
        fun onConfirmDeleteAddresses(withTransactions: Boolean, addresses: List<String>)
        fun onModeChanged(mode: AddressesFragment.Mode)
        fun onSelectAll()
    }

    interface Repository : MvpRepository {
        fun getAddressTags(address: String): List<Tag>
        fun deleteAddress(walletAddress: WalletAddress, withTransactions: List<TxDescription>)
    }
}
