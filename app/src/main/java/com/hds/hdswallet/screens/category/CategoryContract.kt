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

package com.hds.hdswallet.screens.category

import com.hds.hdswallet.base_screen.MvpPresenter
import com.hds.hdswallet.base_screen.MvpRepository
import com.hds.hdswallet.base_screen.MvpView
import com.hds.hdswallet.core.entities.OnAddressesData
import com.hds.hdswallet.core.entities.WalletAddress
import com.hds.hdswallet.core.helpers.Tag
import com.hds.hdswallet.core.helpers.TrashManager
import io.reactivex.subjects.Subject

interface CategoryContract {
    interface View: MvpView {
        fun getCategoryId(): String
        fun init(tag: Tag)
        fun displayTabs(display:Boolean)
        fun updateAddresses(tab: Tab, addresses: List<WalletAddress>)
        fun navigateToEditCategory(categoryId: String)
        fun finish()
        fun showAddressDetails(address: WalletAddress)
        fun showConfirmDeleteDialog(categoryName: String)
    }

    interface Presenter: MvpPresenter<View> {
        fun onEditCategoryPressed()
        fun onDeleteCategoryPressed()
        fun onDeleteCategoryConfirmed()
        fun onAddressPressed(address: WalletAddress)
    }

    interface Repository: MvpRepository {
        fun deleteCategory(tag: Tag)
        fun getCategoryFromId(categoryId: String): Tag?
    }
}