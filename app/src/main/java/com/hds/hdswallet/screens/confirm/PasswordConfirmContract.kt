package com.hds.hdswallet.screens.confirm

import com.hds.hdswallet.base_screen.MvpPresenter
import com.hds.hdswallet.base_screen.MvpRepository
import com.hds.hdswallet.base_screen.MvpView

interface PasswordConfirmContract {
    interface View: MvpView {
        fun init(isFingerprintEnable: Boolean)
        fun showFailedFingerprint()
        fun showErrorFingerprint()
        fun showSuccessFingerprint()
        fun showEmptyPasswordError()
        fun showWrongPasswordError()
        fun clearPasswordError()
        fun close(success: Boolean)
    }

    interface Presenter: MvpPresenter<View> {
        fun onCancel()
        fun onSuccessFingerprint()
        fun onFailedFingerprint()
        fun onErrorFingerprint()
        fun onPasswordChanged()
        fun onOkPressed(password: String)

    }

    interface Repository: MvpRepository {
        fun isFingerPrintEnabled(): Boolean
        fun isFaceIDEnabled(): Boolean
        fun checkPassword(password: String): Boolean
    }
}