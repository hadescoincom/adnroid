package com.hds.hdswallet.core.entities.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NotificationDTO(
        var id: String,
        var state: Int,
        var createTime: Long) : Parcelable