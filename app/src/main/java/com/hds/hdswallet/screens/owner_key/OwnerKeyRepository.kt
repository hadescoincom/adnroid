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

package com.hds.hdswallet.screens.owner_key

import com.hds.hdswallet.base_screen.BaseRepository
import com.hds.hdswallet.core.helpers.PreferencesManager

class OwnerKeyRepository: BaseRepository(), OwnerKeyContract.Repository {
    override fun getOwnerKey(): String {
        val pass = PreferencesManager.getString(PreferencesManager.KEY_PASSWORD)

        if (pass.isNullOrBlank()) {
            return ""
        }

        return getResult("getOwnerKey") { wallet?.exportOwnerKey(pass) ?: "" }
    }
}