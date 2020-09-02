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

package com.hds.hdswallet.screens.transactions

import com.hds.hdswallet.base_screen.MvpPresenter
import com.hds.hdswallet.base_screen.MvpRepository
import com.hds.hdswallet.base_screen.MvpView
import com.hds.hdswallet.core.entities.OnTxStatusData
import com.hds.hdswallet.core.entities.TxDescription
import com.hds.hdswallet.core.helpers.TrashManager
import io.reactivex.Observable
import io.reactivex.subjects.Subject
import java.io.File

interface TransactionsContract {
    interface View: MvpView {
        fun init()
        fun showTransactionDetails(txId: String)
        fun configTransactions(transactions: List<TxDescription>)
        fun exportSave(content:String)
        fun exportShare(file: File)
        fun showProofVerification()
        fun showSearchTransaction()
        fun changeMode(mode: TransactionsFragment.Mode)
        fun showRepeatTransaction()
        fun showDeleteTransactionsSnackBar()
        fun deleteTransactions()
        fun showInProgressToast()
        fun didSelectAllTransactions(transactions: List<TxDescription>)
        fun didUnSelectAllTransactions()
    }

    interface Presenter: MvpPresenter<View> {
        fun onTransactionPressed(txDescription: TxDescription)
        fun onSearchPressed()
        fun onExportShare()
        fun onExportSave()
        fun onProofVerificationPressed()
        fun onModeChanged(mode: TransactionsFragment.Mode)
        fun onRepeatTransaction()
        fun onConfirmDeleteTransactions(transactions: List<String>)
        fun onDeleteTransactionsPressed()
        fun onSelectAll()
    }

    interface Repository: MvpRepository {
        fun getTransactionsFile(): File
        fun deleteTransaction(txDescription: TxDescription?)
    }
}