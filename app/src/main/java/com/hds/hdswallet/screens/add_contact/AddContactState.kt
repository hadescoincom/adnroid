package com.hds.hdswallet.screens.add_contact

import com.hds.hdswallet.core.entities.TxDescription
import com.hds.hdswallet.core.entities.WalletAddress
import com.hds.hdswallet.core.helpers.Tag

class AddContactState {
    var tags: List<Tag> = listOf()

    var addresses = HashMap<String, WalletAddress>()

    fun getAddresses() = addresses.values.toList()
}