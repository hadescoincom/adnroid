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

import com.hds.hdswallet.core.entities.WalletAddress

class ChangeAddressState {
    var generatedAddress: WalletAddress? = null
    private val addresses = HashMap<String, WalletAddress>()
    var scannedAddress : String? = null
    var viewState = ChangeAddressContract.ViewState.Receive

    fun updateAddresses(walletAddresses: List<WalletAddress>?) {
        walletAddresses?.forEach {
            addresses[it.walletID] = it
        }
    }

    fun deleteAddresses(walletAddresses: List<WalletAddress>?) {
        walletAddresses?.forEach { addresses.remove(it.walletID) }
    }


    fun getAddresses() = ArrayList(addresses.apply { remove(generatedAddress?.walletID) }.values.toList()).apply {
        generatedAddress?.let { add(0, it) }
    }

}