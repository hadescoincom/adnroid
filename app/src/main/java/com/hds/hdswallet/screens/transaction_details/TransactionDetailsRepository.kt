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

import android.graphics.Bitmap
import com.hds.hdswallet.base_screen.BaseRepository
import com.hds.hdswallet.core.AppConfig
import com.hds.hdswallet.core.entities.*
import com.hds.hdswallet.core.helpers.Tag
import com.hds.hdswallet.core.helpers.TagHelper
import com.hds.hdswallet.core.helpers.PreferencesManager
import com.hds.hdswallet.core.helpers.TrashManager
import com.hds.hdswallet.core.listeners.WalletListener
import io.reactivex.Observable
import io.reactivex.subjects.Subject
import java.io.File
import java.io.FileOutputStream

/**
 *  10/18/18.
 */
class TransactionDetailsRepository : BaseRepository(), TransactionDetailsContract.Repository {

    override fun deleteTransaction(txDescription: TxDescription?) {
        if (txDescription != null) {
            TrashManager.add(txDescription.id, txDescription)
        }
    }

    override fun getUtxoByTx(txId: String): Subject<List<Utxo>?> {
        return getResult(WalletListener.subOnCoinsByTx, "getUtxoByTx") {
            wallet?.getCoinsByTx(txId)
        }
    }

    override fun getTxStatus(): Observable<OnTxStatusData> {
        return getResult(WalletListener.obsOnTxStatus, "getTxStatus") {
            wallet?.getWalletStatus()
        }
    }

    override fun cancelTransaction(txDescription: TxDescription?) {
        if (txDescription != null) {
            getResult("cancelTransaction", "kernelID = ${txDescription.kernelId}") {
                wallet?.cancelTx(txDescription.id)
            }
        }
    }

    override fun getPaymentProof(txId: String, canRequestProof: Boolean): Subject<PaymentProof> {
        return getResult(WalletListener.subOnPaymentProofExported, "getPaymentProof") {
            if (canRequestProof) {
                requestProof(txId)
            }
        } //FLAG_SECURE
    }

    override fun requestProof(txId: String) {
        getResult("requestProof") {
            wallet?.getPaymentInfo(txId)
        }
    }

    override fun getAddressTags(address: String): List<Tag> {
        return TagHelper.getTagsForAddress(address)
    }

    override fun isAllowOpenExternalLink(): Boolean {
        return PreferencesManager.getBoolean(PreferencesManager.KEY_ALWAYS_OPEN_LINK)
    }

    override fun saveImage(bitmap: Bitmap?): File? {
        val file = File(AppConfig.CACHE_PATH, "tx_desc" + System.currentTimeMillis() + ".png")

        if (!file.parentFile.exists()) {
            file.parentFile.mkdir()
        } else {
            file.parentFile.listFiles().forEach { it.delete() }
        }
        file.createNewFile()

        try {
            val out = FileOutputStream(file)
            bitmap?.compress(Bitmap.CompressFormat.PNG, 100, out)
            out.flush()
            out.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return file
    }

}
