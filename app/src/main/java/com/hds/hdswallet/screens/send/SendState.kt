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

package com.hds.hdswallet.screens.send

import com.hds.hdswallet.core.entities.WalletAddress
import com.hds.hdswallet.core.entities.WalletStatus
import com.hds.hdswallet.core.helpers.ExpirePeriod
import com.hds.hdswallet.core.helpers.Tag

/**
 *  1/2/19.
 */
class SendState {
    var tags = mutableListOf<Tag>()
    var isPastedText = false
    var isNeedGenerateNewAddress = true
    var wasAddressSaved: Boolean = false
    var expirePeriod: ExpirePeriod = ExpirePeriod.DAY
    var walletStatus: WalletStatus? = null
    var privacyMode = false
    val addresses = HashMap<String, WalletAddress>()
    var scannedAddress : String? = null
    var scannedAmount: Double? = null
    var prevFee = 0L
    var outgoingAddress: WalletAddress? = null
    var generatedAddress: WalletAddress? = null
    var expandAdvanced = false
    var expandEditAddress = false
}
