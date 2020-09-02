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

package com.hds.hdswallet.core

import com.hds.hdswallet.BuildConfig
import com.hds.hdswallet.core.entities.OnSyncProgressData
import com.hds.hdswallet.core.entities.Wallet
import io.reactivex.subjects.PublishSubject
import java.io.File
import android.content.Context.DOWNLOAD_SERVICE
import android.app.DownloadManager
import android.net.Uri
import android.os.Handler
import com.hds.hdswallet.R
import android.util.Log


/**
 *  10/1/18.
 */


object Api {
   // var isLoaded = false

    init {
        System.loadLibrary("wallet-jni")
    }

    external fun createWallet(appVersion: String, nodeAddr: String, dbPath: String, pass: String, phrases: String, restore: Boolean = false): Wallet?
    external fun openWallet(appVersion: String, nodeAddr: String, dbPath: String, pass: String): Wallet?
    external fun isWalletInitialized(dbPath: String): Boolean
    external fun createMnemonic(): Array<String>
    external fun getDictionary(): Array<String>
    external fun checkReceiverAddress(address: String?): Boolean
    external fun closeWallet()
    external fun isWalletRunning(): Boolean
    external fun getDefaultPeers(): Array<String>
}
