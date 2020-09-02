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

package com.hds.hdswallet.screens.address_edit

import com.hds.hdswallet.base_screen.BaseRepository
import com.hds.hdswallet.core.AppManager
import com.hds.hdswallet.core.entities.TxDescription
import com.hds.hdswallet.core.entities.WalletAddress
import com.hds.hdswallet.core.entities.dto.WalletAddressDTO
import com.hds.hdswallet.core.helpers.Tag
import com.hds.hdswallet.core.helpers.TagHelper
import com.hds.hdswallet.core.helpers.TrashManager

/**
 *  3/5/19.
 */
class EditAddressRepository : BaseRepository(), EditAddressContract.Repository {

    override fun deleteAddress(walletAddress: WalletAddress, txDescriptions: List<TxDescription>) {
        getResult("deleteAddress") {
            TrashManager.add(walletAddress.walletID, TrashManager.ActionData(txDescriptions, listOf(walletAddress)))
        }
    }

    override fun saveAddressChanges(addr: String, name: String, isNever: Boolean, makeActive: Boolean, makeExpired: Boolean) {
        getResult("saveAddressChanges") {
            var addressExpiration = WalletAddressDTO.WalletAddressExpirationStatus.OneDay

            if(makeExpired) {
                addressExpiration = WalletAddressDTO.WalletAddressExpirationStatus.Expired;
            }
            else if(isNever) {
                addressExpiration = WalletAddressDTO.WalletAddressExpirationStatus.Never;
            }

            if(addressExpiration == WalletAddressDTO.WalletAddressExpirationStatus.Expired) {
                AppManager.instance.ignoreNotifications.add(addr)
            }

            wallet?.updateAddress(addr, name, addressExpiration.ordinal)
        }
    }

    override fun saveAddress(address: WalletAddress, own: Boolean) {
        getResult("saveAddress") {
            wallet?.saveAddress(address.toDTO(), own)
        }
    }

    override fun updateAddress(address: WalletAddress) {
        getResult("updateAddress") {
            val addressExpiration = when {
                address.isExpired -> WalletAddressDTO.WalletAddressExpirationStatus.Expired
                address.duration == 0L -> WalletAddressDTO.WalletAddressExpirationStatus.Never
                else -> WalletAddressDTO.WalletAddressExpirationStatus.OneDay
            }

            if(addressExpiration == WalletAddressDTO.WalletAddressExpirationStatus.Expired) {
                AppManager.instance.ignoreNotifications.add(address.walletID)
            }

            wallet?.updateAddress(address.walletID, address.label, addressExpiration.ordinal)
        }
    }

    override fun getAddressTags(address: String): List<Tag> {
        return TagHelper.getTagsForAddress(address)
    }

    override fun getAllTags(): List<Tag> {
        return TagHelper.getAllTags()
    }

    override fun saveTagsForAddress(address: String, tags: List<Tag>) {
        TagHelper.changeTagsForAddress(address, tags)
    }
}
