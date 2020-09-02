package com.hds.hdswallet.screens.welcome_screen.restore_owner_key

import com.hds.hdswallet.base_screen.BaseRepository
import com.hds.hdswallet.core.Api
import com.hds.hdswallet.core.AppConfig
import com.hds.hdswallet.core.helpers.*
import com.hds.hdswallet.core.utils.LogUtils
import com.hds.hdswallet.core.AppManager

class RestoreOwnerKeyRepository: BaseRepository(), RestoreOwnerKeyContract.Repository {
    override fun getOwnerKey(pass: String): String {
        return getResult("getOwnerKey") { wallet?.exportOwnerKey(pass) ?: "" }
    }

    override fun createWallet(pass: String?, seed: String?): Status {
        var result = Status.STATUS_ERROR

        if (!pass.isNullOrBlank() && seed != null) {
            if (Api.isWalletInitialized(AppConfig.DB_PATH)) {
                removeDatabase()
                removeNodeDatabase()
            }

            AppManager.instance.wallet = Api.createWallet(AppConfig.APP_VERSION, "", AppConfig.DB_PATH, pass, seed)

            if (wallet != null) {
                PreferencesManager.putString(PreferencesManager.KEY_PASSWORD, pass)
                result = Status.STATUS_OK
            }
        }

        LogUtils.logResponse(result, "createWallet")
        return result
    }
}