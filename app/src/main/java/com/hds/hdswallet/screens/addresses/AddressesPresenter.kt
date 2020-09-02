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

import com.hds.hdswallet.base_screen.BasePresenter
import com.hds.hdswallet.core.AppManager
import com.hds.hdswallet.core.entities.WalletAddress
import com.hds.hdswallet.core.helpers.ChangeAction
import com.hds.hdswallet.core.helpers.Tag
import com.hds.hdswallet.core.helpers.TrashManager
import io.reactivex.disposables.Disposable

/**
 *  2/28/19.
 */
class AddressesPresenter(currentView: AddressesContract.View, currentRepository: AddressesContract.Repository, val state: AddressesState)
    : BasePresenter<AddressesContract.View, AddressesContract.Repository>(currentView, currentRepository),
        AddressesContract.Presenter {

    private lateinit var addressesSubscription: Disposable
    var isAllSelected = false

    override fun onViewCreated() {
        super.onViewCreated()
        view?.init()
    }

    override fun onAddressPressed(address: WalletAddress) {
        view?.showAddressDetails(address)
    }

    override fun initSubscriptions() {
        super.initSubscriptions()

        state.addresses.clear()
        state.addresses.addAll(AppManager.instance.getAllAddresses())

        addressesSubscription = AppManager.instance.subOnAddressesChanged.subscribe(){
            state.addresses.clear()
            state.addresses.addAll(AppManager.instance.getAllAddresses())
            updateView()
        }

        updateView()
    }

    override fun onAddContactPressed() {
        view?.navigateToAddContactScreen()
    }

    override fun onEditAddressPressed() {
        view?.navigateToEditAddressScreen()
    }

    override fun onCopyAddressPressed() {
        view?.copyAddress()
    }

    override fun onDeleteAddressesPressed() {
        view?.deleteAddresses()
    }

    override fun onSelectAll() {
        isAllSelected = !isAllSelected

        if(isAllSelected)
        {
            view?.didSelectAllAddresses(state.addresses)
        }
        else{
            view?.didUnSelectAllAddresses()
        }
    }

    override fun onDeleteAddress(selected: List<String>) {
        var showTransactionsAlert = false

        selected?.forEach { walletID ->
            if (state.getTransactions(walletID).count() > 0) {
                showTransactionsAlert = true
                return@forEach
            }
        }

        if (showTransactionsAlert) {
            view?.showDeleteAddressesDialog(true)
        } else {
            view?.showDeleteAddressesDialog(false)
        }
    }

    override fun onConfirmDeleteAddresses(withTransactions: Boolean, addresses: List<String>) {
        for (i in 0 until addresses.count()) {
            val id = addresses[i]
            val address = state?.addresses?.find { it.walletID == id }
            if (address != null) {
                repository.deleteAddress(address, if (withTransactions) state?.getTransactions(id) else listOf())
            }
        }
    }

    private fun updateView() {
        val addresses = state.addresses

        view?.updateAddresses(Tab.ACTIVE, addresses.filter { !it.isExpired && !it.isContact })
        view?.updateAddresses(Tab.EXPIRED, addresses.filter { it.isExpired && !it.isContact })
        view?.updateAddresses(Tab.CONTACTS, addresses.filter { it.isContact })
        view?.updatePlaceholder(addresses.count() == 0)
    }

    override fun onSearchTagsForAddress(address: String): List<Tag> {
        return repository.getAddressTags(address)
    }

    override fun getSubscriptions(): Array<Disposable>? = arrayOf(addressesSubscription)

    override fun hasBackArrow(): Boolean? = true
    override fun hasStatus(): Boolean = true

    override fun onModeChanged(mode: AddressesFragment.Mode) {
        view?.changeMode(mode)
    }
}
