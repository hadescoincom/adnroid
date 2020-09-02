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

package com.hds.hdswallet.screens.save_address

import com.hds.hdswallet.base_screen.MvpPresenter
import com.hds.hdswallet.base_screen.MvpRepository
import com.hds.hdswallet.base_screen.MvpView
import com.hds.hdswallet.core.entities.WalletAddress
import com.hds.hdswallet.core.helpers.Tag

interface SaveAddressContract {
    interface View: MvpView {
        fun getAddress(): String
        fun init(address: String)
        fun showAddNewCategory()
        fun getName(): String
        fun close()
        fun setupTagAction(isEmptyTags: Boolean)
        fun showTagsDialog(selectedTags: List<Tag>)
        fun showCreateTagDialog()
        fun setTags(tags: List<Tag>)
    }
    interface Presenter: MvpPresenter<View> {
        fun onSavePressed()
        fun onCancelPressed()
        fun onTagActionPressed()
        fun onSelectTags(tags: List<Tag>)
        fun onCreateNewTagPressed()
    }
    interface Repository: MvpRepository {
        fun saveAddress(address: WalletAddress, own: Boolean)
        fun getAddressTags(address: String): List<Tag>
        fun getAllTags(): List<Tag>
        fun saveTagsForAddress(address: String, tags: List<Tag>)
    }
}