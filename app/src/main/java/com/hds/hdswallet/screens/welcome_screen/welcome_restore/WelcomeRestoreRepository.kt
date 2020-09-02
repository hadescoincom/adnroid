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
package com.hds.hdswallet.screens.welcome_screen.welcome_restore

import com.hds.hdswallet.base_screen.BaseRepository
import com.hds.hdswallet.core.Api
import com.hds.hdswallet.core.helpers.removeDatabase

/**
 *  11/5/18.
 */
class WelcomeRestoreRepository : BaseRepository(), WelcomeRestoreContract.Repository {

    override fun restoreWallet(): Boolean {
        removeDatabase()
        return true
    }

    override fun getSuggestions(): List<String> {
        return getResult("getSuggestions") {
            Api.getDictionary().asList()
        }
    }
}
