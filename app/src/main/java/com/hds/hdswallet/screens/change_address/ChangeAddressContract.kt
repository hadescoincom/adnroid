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

import com.hds.hdswallet.base_screen.MvpPresenter
import com.hds.hdswallet.base_screen.MvpRepository
import com.hds.hdswallet.base_screen.MvpView
import com.hds.hdswallet.core.entities.OnAddressesData
import com.hds.hdswallet.core.entities.WalletAddress
import com.hds.hdswallet.core.helpers.Tag
import com.hds.hdswallet.core.helpers.TrashManager
import io.reactivex.subjects.Subject

interface ChangeAddressContract {
    interface View: MvpView {
        fun isFromReceive(): Boolean
        fun init(state: ViewState, generatedAddress: WalletAddress?)
        fun getGeneratedAddress(): WalletAddress?
        fun updateList(items: List<WalletAddress>)
        fun back(walletAddress: WalletAddress?)
        fun setAddress(address: String)
        fun getSearchText(): String
        fun showNotHdsAddressError()
    }

    interface Presenter: MvpPresenter<View> {
        fun onChangeSearchText(text: String)
        fun onItemPressed(walletAddress: WalletAddress)
        fun onSearchTagsForAddress(address: String): List<Tag>
    }

    interface Repository: MvpRepository {
        fun getAddresses(): Subject<OnAddressesData>
        fun getTrashSubject(): Subject<TrashManager.Action>
        fun getAllAddressesInTrash(): List<WalletAddress>
        fun getAddressTags(address: String): List<Tag>
    }

    enum class ViewState {
        Send, Receive
    }
}