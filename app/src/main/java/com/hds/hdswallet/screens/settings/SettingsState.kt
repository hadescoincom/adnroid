package com.hds.hdswallet.screens.settings

import com.hds.hdswallet.core.AppManager
import com.hds.hdswallet.core.entities.TxDescription
import com.hds.hdswallet.core.entities.WalletAddress

class SettingsState {

    val addresses: List<WalletAddress>
        get() = AppManager.instance.getMyAddresses()

    val contacts: List<WalletAddress>
        get() = AppManager.instance.getContacts()

    val transactions: List<TxDescription>
        get() = AppManager.instance.getTransactions()
}