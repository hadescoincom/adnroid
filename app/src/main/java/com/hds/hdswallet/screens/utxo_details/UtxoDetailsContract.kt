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

package com.hds.hdswallet.screens.utxo_details

import android.graphics.Bitmap
import com.hds.hdswallet.base_screen.MvpPresenter
import com.hds.hdswallet.base_screen.MvpRepository
import com.hds.hdswallet.base_screen.MvpView
import com.hds.hdswallet.core.entities.OnTxStatusData
import com.hds.hdswallet.core.entities.TxDescription
import com.hds.hdswallet.core.entities.Utxo
import com.hds.hdswallet.core.helpers.TrashManager
import io.reactivex.Observable
import io.reactivex.subjects.Subject
import java.io.File

/**
 *  12/20/18.
 */
interface UtxoDetailsContract {
    interface View : MvpView {
        fun getUtxo(): Utxo
        fun init(utxo: Utxo)
        fun configUtxoHistory(utxo: Utxo, relatedTransactions: List<TxDescription>?)
        fun handleExpandDetails(shouldExpandDetails: Boolean)
        fun handleExpandTransactions(shouldExpandTransactions: Boolean)
    }

    interface Presenter : MvpPresenter<View> {
        fun onExpandDetailedPressed()
        fun onExpandTransactionsPressed()
    }

    interface Repository : MvpRepository {

    }
}
