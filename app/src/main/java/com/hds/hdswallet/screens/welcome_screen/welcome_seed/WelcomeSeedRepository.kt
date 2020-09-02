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
package com.hds.hdswallet.screens.welcome_screen.welcome_seed

import com.hds.hdswallet.base_screen.BaseRepository
import com.hds.hdswallet.core.Api
import com.hds.hdswallet.core.App
import com.hds.hdswallet.core.AppManager
import com.hds.hdswallet.core.OnboardManager

/**
 *  10/30/18.
 */
class WelcomeSeedRepository : BaseRepository(), WelcomeSeedContract.Repository {

    override fun seed(): Array<String> {
        if (App.isAuthenticated) {
            val seed = OnboardManager.instance.getSeed()
            if (seed!=null) {
                val lstValues: List<String> = seed.split(";").map { it -> it.trim() }.dropLast(1)
                return lstValues.toTypedArray()
            }
            else{
                return Api.createMnemonic()
            }
        }
        else{
            return Api.createMnemonic()
        }
    }
}
