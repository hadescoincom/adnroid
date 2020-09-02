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

package com.hds.hdswallet.screens.send

import com.hds.hdswallet.base_screen.BaseRepository
import com.hds.hdswallet.core.Api
import com.hds.hdswallet.core.entities.OnAddressesData
import com.hds.hdswallet.core.entities.WalletAddress
import com.hds.hdswallet.core.entities.WalletStatus
import com.hds.hdswallet.core.helpers.Tag
import com.hds.hdswallet.core.helpers.TagHelper
import com.hds.hdswallet.core.helpers.PreferencesManager
import com.hds.hdswallet.core.helpers.TrashManager
import com.hds.hdswallet.core.listeners.WalletListener
import io.reactivex.subjects.Subject

/**
 *  11/13/18.
 */
class SendRepository : BaseRepository(), SendContract.Repository {

    override fun checkAddress(address: String?): Boolean {
        return Api.checkReceiverAddress(address)
    }

    override fun isConfirmTransactionEnabled(): Boolean {
        return PreferencesManager.getBoolean(PreferencesManager.KEY_IS_SENDING_CONFIRM_ENABLED)
    }

    override fun onCantSendToExpired(): Subject<Any> {
        return getResult(WalletListener.subOnCantSendToExpired, "onCantSendToExpired")
    }

    override fun generateNewAddress(): Subject<WalletAddress> {
        return getResult(WalletListener.subOnGeneratedNewAddress, "generateNewAddress") {
            wallet?.generateNewAddress()
        }
    }

    override fun updateAddress(address: WalletAddress) {
        getResult("updateAddress") {
            val isNever = address.duration == 0L
          //  wallet?.saveAddressChanges(address.walletID, address.label, isNever, makeActive = !isNever, makeExpired = false)
        }
    }

    override fun saveAddress(address: WalletAddress) {
        getResult("updateAddress") {
            wallet?.saveAddress(address.toDTO(), true)
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

    override fun isNeedConfirmEnablePrivacyMode(): Boolean = PreferencesManager.getBoolean(PreferencesManager.KEY_PRIVACY_MODE_NEED_CONFIRM, true)
}
