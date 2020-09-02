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

package com.hds.hdswallet.screens.category

import com.hds.hdswallet.core.AppManager
import com.hds.hdswallet.core.entities.WalletAddress
import com.hds.hdswallet.core.helpers.Tag
import com.hds.hdswallet.core.helpers.TagHelper

class CategoryState {
    var tag: Tag? = null

    fun allCount():Int{
        if (tag==null) {
            return 0
        }
        else{
            val addresses= AppManager.instance.getAllAddresses()
            val list= TagHelper.getAddressesForTag(tag?.id)
            return list.count()
        }
    }

    fun getAddresses(tab: Tab):List<WalletAddress> {
        if (tag==null) {
            return listOf<WalletAddress>()
        }
        else{
            val addresses= AppManager.instance.getAllAddresses()
            val list= TagHelper.getAddressesForTag(tag?.id)
            return list.filter {
                when(tab) {
                    Tab.ADDRESSES -> it.isContact == false
                    Tab.CONTACTS -> it.isContact == true
                }
            }
        }
    }

}