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

package com.hds.hdswallet.screens.app_activity

import com.hds.hdswallet.base_screen.BaseRepository
import com.hds.hdswallet.core.AppManager
import com.hds.hdswallet.core.entities.WalletAddress
import com.hds.hdswallet.core.helpers.TrashManager

class AppActivityRepository: BaseRepository(), AppActivityContract.Repository {
    override fun sendMoney(outgoingAddress: String, token: String, comment: String?, amount: Long, fee: Long) {
        getResult("sendMoney", " sender: $outgoingAddress\n token: $token\n comment: $comment\n amount: $amount\n fee: $fee") {

            val address = AppManager.instance.getAddress(token)
            val name= address?.label

            wallet?.sendMoney(outgoingAddress, token, comment ?: "", amount, fee)

            if(address!=null && name!=null) {
                val dto = address.toDTO()
                dto.label = name
                wallet?.saveAddress(dto, address.isContact)
            }

            removeSenContact(token)
        }
    }

    override fun cancelSendMoney(token: String) {
       removeSenContact(token)
    }

    private fun removeSenContact(token:String) {
        android.os.Handler().postDelayed({
            var address:WalletAddress? = null
            TrashManager.getAllData().addresses.forEach {
                if (it.walletID == token)
                {
                    address = it
                }
            }

            if (address!=null) {
                TrashManager.remove(token)
            }
        }, 1000)
    }
}