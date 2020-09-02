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

import android.view.Menu
import com.hds.hdswallet.base_screen.MvpPresenter
import com.hds.hdswallet.base_screen.MvpRepository
import com.hds.hdswallet.base_screen.MvpView
import com.hds.hdswallet.core.entities.TxDescription
import com.hds.hdswallet.core.entities.WalletAddress
import com.hds.hdswallet.core.helpers.Tag
import com.hds.hdswallet.core.helpers.ExpirePeriod

/**
 *  3/5/19.
 */
interface EditAddressContract {
    interface View : MvpView {
        fun getAddress(): WalletAddress
        fun init(address: WalletAddress)
        fun configExpireSpinnerTime(shouldExpireNow: Boolean)
        fun configSaveButton(shouldEnable: Boolean)
        fun finishScreen()
        fun onAddressDeleted()
        fun showAddNewCategory()
        fun setupTagAction(isEmptyTags: Boolean)
        fun showTagsDialog(selectedTags: List<Tag>)
        fun showCreateTagDialog()
        fun setTags(tags: List<Tag>)
        fun configMenuItems(menu: Menu?, address: WalletAddress)
        fun showDeleteAddressDialog(transactionAlert:Boolean)
        fun showDeleteSnackBar(walletAddress: WalletAddress)
    }

    interface Presenter : MvpPresenter<View> {
        fun onSwitchCheckedChange(isChecked: Boolean)
        fun onExpirePeriodChanged(period : ExpirePeriod)
        fun onSavePressed()
        fun onChangeComment(comment: String)
        fun onTagActionPressed()
        fun onSelectTags(tags: List<Tag>)
        fun onCreateNewTagPressed()
        fun onMenuCreate(menu: Menu?)
        fun onDeleteAddress()
        fun onConfirmDeleteAddress(withTransactions: Boolean)
    }

    interface Repository : MvpRepository {
        fun saveAddressChanges(addr: String, name: String, isNever: Boolean, makeActive: Boolean, makeExpired: Boolean)
        fun updateAddress(address: WalletAddress)
        fun getAddressTags(address: String): List<Tag>
        fun getAllTags(): List<Tag>
        fun saveTagsForAddress(address: String, tags: List<Tag>)
        fun saveAddress(address: WalletAddress, own: Boolean)
        fun deleteAddress(walletAddress: WalletAddress, txDescriptions: List<TxDescription>)
    }
}
