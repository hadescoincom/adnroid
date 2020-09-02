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

import android.view.Menu
import android.view.MenuInflater
import com.hds.hdswallet.base_screen.MvpPresenter
import com.hds.hdswallet.base_screen.MvpRepository
import com.hds.hdswallet.base_screen.MvpView
import com.hds.hdswallet.core.entities.NotificationItem


interface NotifcationsContract {
    interface View : MvpView {
        fun init()
        fun configNotifications(notifications: List<NotificationItem>, isEnablePrivacyMode: Boolean)
        fun createOptionsMenu(menu: Menu?, inflater: MenuInflater?, isEnablePrivacyMode: Boolean)
        fun showActivatePrivacyModeDialog()
        fun configPrivacyStatus(isEnable: Boolean)
        fun openTransactionFragment(id:String)
        fun openAddressFragment(id:String)
        fun openNewVersionFragment(value: String);
    }

    interface Presenter : MvpPresenter<View> {
        fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?)
        fun onChangePrivacyModePressed()
        fun onPrivacyModeActivated()
        fun onCancelDialog()
        fun onOpenNotification(notification: NotificationItem)
        fun deleteAllNotifications()
        fun deleteNotifications(list: List<String>)
    }

    interface Repository : MvpRepository {
        fun getNotifications(): List<NotificationItem>
        fun isNeedConfirmEnablePrivacyMode(): Boolean
    }
}
