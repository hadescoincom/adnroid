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

package com.hds.hdswallet.screens.welcome_screen.welcome_confirm

import com.hds.hdswallet.base_screen.BaseRepository
import com.hds.hdswallet.core.Api
import java.util.*
import kotlin.collections.HashSet

/**
 *  11/1/18.
 */
class WelcomeConfirmRepository : BaseRepository(), WelcomeConfirmContract.Repository {
    override var seed: Array<String>? = null

    override fun getSeedToValidate(): List<Int> {
        val set = HashSet<Int>()
        val ran = Random()

        while (set.size < 6) {
            set.add(ran.nextInt(11) + 1)
        }

        return set.shuffled()
    }

    override fun getSuggestions(): List<String> {
        return getResult("getSuggestions") {
            Api.getDictionary().asList()
        }
    }
}
