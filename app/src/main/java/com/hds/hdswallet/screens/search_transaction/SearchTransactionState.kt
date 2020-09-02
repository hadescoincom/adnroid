package com.hds.hdswallet.screens.search_transaction

import com.hds.hdswallet.core.AppManager
import com.hds.hdswallet.core.entities.TxDescription
import com.hds.hdswallet.core.entities.WalletAddress

class SearchTransactionState {
    var searchText = ""

    fun getAddresses() = AppManager.instance.getAllAddresses()

    fun getAllTransactions() = AppManager.instance.getTransactions()
}