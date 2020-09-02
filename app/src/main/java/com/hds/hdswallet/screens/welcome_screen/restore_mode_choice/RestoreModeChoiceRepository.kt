package com.hds.hdswallet.screens.welcome_screen.restore_mode_choice

import com.hds.hdswallet.base_screen.BaseRepository
import com.hds.hdswallet.core.AppManager
import com.hds.hdswallet.core.helpers.PreferencesManager
import com.hds.hdswallet.core.helpers.removeDatabase
import com.hds.hdswallet.core.helpers.removeNodeDatabase

class RestoreModeChoiceRepository: BaseRepository(), RestoreModeChoiceContract.Repository {
    override fun removeWallet() {
        AppManager.instance.wallet = null

        PreferencesManager.putString(PreferencesManager.KEY_NODE_ADDRESS,"")
        PreferencesManager.putBoolean(PreferencesManager.KEY_CONNECT_TO_RANDOM_NODE,true)

        removeDatabase()

        removeNodeDatabase()
    }

    override fun saveStartRestoreFlag() {
        PreferencesManager.putBoolean(PreferencesManager.KEY_UNFINISHED_RESTORE, true)
    }
}