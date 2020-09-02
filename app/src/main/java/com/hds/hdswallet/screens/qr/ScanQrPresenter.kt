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

package com.hds.hdswallet.screens.qr

import android.net.Uri
import com.hds.hdswallet.base_screen.BasePresenter
import com.hds.hdswallet.core.helpers.PermissionStatus

/**
 *  3/15/19.
 */
class ScanQrPresenter (currentView: ScanQrContract.View, currentRepository: ScanQrContract.Repository)
    : BasePresenter<ScanQrContract.View, ScanQrContract.Repository>(currentView, currentRepository),
        ScanQrContract.Presenter {

    override fun onQrFromGalleryPressed() {
        view?.pickImageFromGallery()
    }

    override fun onRequestPermissionsResult(status: PermissionStatus, code:Int) {
        when (status) {
            PermissionStatus.GRANTED -> if(code==5421) view?.pickImageFromGallery()
            PermissionStatus.NEVER_ASK_AGAIN -> {
                view?.showPermissionRequiredAlert()
            }
            PermissionStatus.DECLINED -> {}
        }
    }

    override fun onImageSelected(uri: Uri?) {
        val result = view?.readQrCode(uri)
        if (result != null && !result.text.isNullOrBlank()) {
            view?.finishWithResult(result)
        } else {
            view?.showNotFoundQrCodeError()
        }
    }

    override fun hasStatus(): Boolean = true
}

