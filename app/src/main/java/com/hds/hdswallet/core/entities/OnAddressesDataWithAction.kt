package com.hds.hdswallet.core.entities

import com.hds.hdswallet.core.helpers.ChangeAction


data class OnAddressesDataWithAction(val action: ChangeAction, val addresses: List<WalletAddress>?)