package com.hds.hdswallet.screens.confirm

import com.hds.hdswallet.base_screen.BaseRepository
import com.hds.hdswallet.core.helpers.FaceIDManager
import com.hds.hdswallet.core.helpers.FingerprintManager
import com.hds.hdswallet.core.helpers.PreferencesManager

class PasswordConfirmRepository: BaseRepository(), PasswordConfirmContract.Repository {

    override fun isFingerPrintEnabled(): Boolean {
        return PreferencesManager.getBoolean(PreferencesManager.KEY_IS_FINGERPRINT_ENABLED) && FingerprintManager.isManagerAvailable()

    }

    override fun isFaceIDEnabled(): Boolean {
        return PreferencesManager.getBoolean(PreferencesManager.KEY_IS_FINGERPRINT_ENABLED)
                && FaceIDManager.isManagerAvailable()
    }

    override fun checkPassword(password: String): Boolean {
        return wallet?.checkWalletPassword(password) ?: false
    }
}