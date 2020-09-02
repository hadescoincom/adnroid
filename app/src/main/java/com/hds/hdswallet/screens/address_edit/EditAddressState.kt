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

package com.hds.hdswallet.screens.address_edit

import com.hds.hdswallet.core.AppManager
import com.hds.hdswallet.core.entities.TxDescription
import com.hds.hdswallet.core.entities.WalletAddress
import com.hds.hdswallet.core.helpers.Tag
import com.hds.hdswallet.core.helpers.ExpirePeriod

/**
 *  3/5/19.
 */
class EditAddressState {
    var address: WalletAddress? = null
    var tempComment: String = ""
    var shouldExpireNow = false
    var shouldActivateNow = false
    var currentTags: List<Tag> = listOf()
    var tempTags: List<Tag> = listOf()
    lateinit var chosenPeriod: ExpirePeriod

    fun getTransactions():List<TxDescription> {
        return AppManager.instance.getTransactionsByAddress(address?.walletID)
    }
}
