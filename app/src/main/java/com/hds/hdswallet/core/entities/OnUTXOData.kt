package com.hds.hdswallet.core.entities

import com.hds.hdswallet.core.helpers.ChangeAction
import com.hds.hdswallet.core.helpers.prepareForLog

data class OnUTXOData(val action: ChangeAction, val utxo: List<Utxo>?) {
    override fun toString(): String {
        return "OnUTXOData(action=$action, tx=${utxo?.prepareForLog()})"
    }
}
