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

package com.hds.hdswallet.core.entities.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 *  1/4/19.
 */
@Parcelize
data class TxDescriptionDTO(var id: String,
                            var amount: Long,
                            var fee: Long,
                            var change: Long,
                            var minHeight: Long,
                            var peerId: String,
                            var myId: String,
                            var message: String?,
                            var createTime: Long,
                            var modifyTime: Long,
                            var sender: Boolean,
                            var status: Int,
                            var kernelId: String,
                            var selfTx : Boolean,
                            var failureReason : Int) : Parcelable
