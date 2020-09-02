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

package com.hds.hdswallet.screens.welcome_screen.welcome_progress

import com.hds.hdswallet.core.helpers.WelcomeMode

/**
 *  2/5/19.
 */
class WelcomeProgressState {
    var mode : WelcomeMode = WelcomeMode.CREATE
    var password = ""
    var seed : Array<String>? = null
    var isFailedNetworkConnect = false
}
