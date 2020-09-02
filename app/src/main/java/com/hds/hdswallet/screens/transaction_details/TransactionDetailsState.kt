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

package com.hds.hdswallet.screens.transaction_details

import com.hds.hdswallet.core.entities.PaymentProof
import com.hds.hdswallet.core.entities.TxDescription
import com.hds.hdswallet.core.entities.WalletAddress

class TransactionDetailsState {
    var shouldExpandDetail = true
    var shouldExpandUtxos = true
    var shouldExpandProof = true

    var txDescription: TxDescription? = null
    var txID: String? = null
    var paymentProof: PaymentProof? = null
}
