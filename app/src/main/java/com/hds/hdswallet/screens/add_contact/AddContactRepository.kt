package com.hds.hdswallet.screens.add_contact

import com.hds.hdswallet.base_screen.BaseRepository
import com.hds.hdswallet.core.entities.OnAddressesData
import com.hds.hdswallet.core.entities.OnTxStatusData
import com.hds.hdswallet.core.entities.TxDescription
import com.hds.hdswallet.core.entities.WalletAddress
import com.hds.hdswallet.core.entities.dto.WalletAddressDTO
import com.hds.hdswallet.core.helpers.Tag
import com.hds.hdswallet.core.helpers.TagHelper
import com.hds.hdswallet.core.helpers.TrashManager
import com.hds.hdswallet.core.listeners.WalletListener
import io.reactivex.Observable
import io.reactivex.subjects.Subject

class AddContactRepository: BaseRepository(), AddContactContract.Repository {

    override fun saveContact(address: String, name: String, tags: List<Tag>) {
        getResult("saveContact") {

            var categories = mutableListOf<String>()
            for (t in tags) {
                categories.add(t.id)
            }
            var ids = categories.joinToString(";")
            wallet?.saveAddress(WalletAddressDTO(address, name, ids, System.currentTimeMillis(), 0, 0), false)
           // TagHelper.changeTagsForAddress(address, tags, name)
        }
    }

    override fun getAddressTags(address: String): List<Tag> {
        return TagHelper.getTagsForAddress(address)
    }

    override fun getAllTags(): List<Tag> {
        return TagHelper.getAllTags()
    }
}