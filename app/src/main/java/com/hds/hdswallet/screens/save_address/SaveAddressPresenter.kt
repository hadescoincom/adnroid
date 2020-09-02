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

import com.hds.hdswallet.base_screen.BasePresenter
import com.hds.hdswallet.core.App
import com.hds.hdswallet.core.AppManager
import com.hds.hdswallet.core.entities.WalletAddress
import com.hds.hdswallet.core.entities.dto.WalletAddressDTO
import com.hds.hdswallet.core.helpers.Tag
import com.hds.hdswallet.core.helpers.TrashManager

class SaveAddressPresenter(view: SaveAddressContract.View?, repository: SaveAddressContract.Repository, private val state: SaveAddressState)
    : BasePresenter<SaveAddressContract.View, SaveAddressContract.Repository>(view, repository), SaveAddressContract.Presenter {

    override fun onViewCreated() {
        super.onViewCreated()
        view?.apply {
            state.address = getAddress()
            state.tags = repository.getAddressTags(state.address)

            init(state.address)
        }
    }

    override fun onStart() {
        super.onStart()
        view?.setupTagAction(repository.getAllTags().isEmpty())
    }

    override fun onSavePressed() {
        view?.apply {
            val address = WalletAddress(WalletAddressDTO(state.address, getName(), "", System.currentTimeMillis(), 0, 0))
            if(state.tags.count() > 0) {
                repository.saveTagsForAddress(state.address, state.tags)
            }
            repository.saveAddress(address, false)
            close()
        }
    }

    override fun onCreateNewTagPressed() {
        view?.showAddNewCategory()
    }

    override fun onSelectTags(tags: List<Tag>) {
        state.tags = tags
        view?.setTags(tags)
    }

    override fun onTagActionPressed() {
        if (repository.getAllTags().isEmpty()) {
            view?.showCreateTagDialog()
        } else {
            view?.showTagsDialog(state.tags)
        }
    }

    override fun onCancelPressed() {
        val address = WalletAddress(WalletAddressDTO(state.address,"deleted","",0L,0L,0L))

        TrashManager.add(state.address, address)

        AppManager.instance.wallet?.deleteAddress(state.address)

        view?.close()
    }

}