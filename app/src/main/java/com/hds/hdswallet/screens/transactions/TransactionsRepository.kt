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

import com.hds.hdswallet.base_screen.BaseRepository
import com.hds.hdswallet.core.AppConfig
import com.hds.hdswallet.core.entities.OnTxStatusData
import com.hds.hdswallet.core.entities.TxDescription
import com.hds.hdswallet.core.helpers.TrashManager
import com.hds.hdswallet.core.listeners.WalletListener
import io.reactivex.Observable
import io.reactivex.subjects.Subject
import java.io.File

class TransactionsRepository: BaseRepository(), TransactionsContract.Repository {
    override fun getTransactionsFile(): File {
        val file = File(AppConfig.TRANSACTIONS_PATH, "transactions_" + System.currentTimeMillis() + ".csv")

        if (!file.parentFile.exists()) {
            file.parentFile.mkdir()
        } else {
            file.parentFile.listFiles().forEach { it.delete() }
        }
        file.createNewFile()

        return file
    }

    override fun deleteTransaction(txDescription: TxDescription?) {
        if (txDescription != null) {
            TrashManager.add(txDescription.id, txDescription)
        }
    }
}