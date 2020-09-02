package com.hds.hdswallet.core.entities

import com.hds.hdswallet.core.helpers.ChangeAction

data class OnNotificationDataWithAction(val action: ChangeAction, val notification: Notification)