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

package com.hds.hdswallet.screens.notifications

import com.hds.hdswallet.R
import com.hds.hdswallet.base_screen.BaseRepository
import com.hds.hdswallet.core.App
import com.hds.hdswallet.core.AppManager
import com.hds.hdswallet.core.entities.NotificationItem
import com.hds.hdswallet.core.helpers.PreferencesManager


class NotificationsRepository : BaseRepository(), NotifcationsContract.Repository {

    override fun getNotifications(): List<NotificationItem> {
        val notifications =  AppManager.instance.getNotifications()
        val unread = mutableListOf<NotificationItem>()
        val read = mutableListOf<NotificationItem>()
        val result = mutableListOf<NotificationItem>()
        val privacy = isPrivacyModeEnabled()

        notifications.forEach {
            if (it.isRead) {
                read.add(NotificationItem(it, privacy))
            }
            else {
                unread.add(NotificationItem(it, privacy))
            }
        }
        read.sortByDescending { it.date  }
        unread.sortByDescending { it.date  }

        result.addAll(unread)
        if(read.count() > 0) {
            read.add(0, NotificationItem(App.self.resources.getString(R.string.read)))
        }
        result.addAll(read)

        return result
    }

    override fun isNeedConfirmEnablePrivacyMode(): Boolean = PreferencesManager.getBoolean(PreferencesManager.KEY_PRIVACY_MODE_NEED_CONFIRM, true)
}
