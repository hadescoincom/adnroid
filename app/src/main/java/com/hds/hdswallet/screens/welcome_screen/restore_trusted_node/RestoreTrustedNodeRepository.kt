package com.hds.hdswallet.screens.welcome_screen.restore_trusted_node

import com.hds.hdswallet.base_screen.BaseRepository
import com.hds.hdswallet.core.AppManager
import com.hds.hdswallet.core.AppConfig
import com.hds.hdswallet.core.helpers.PreferencesManager
import io.reactivex.subjects.Subject
import com.hds.hdswallet.core.listeners.WalletListener

class RestoreTrustedNodeRepository: BaseRepository(), RestoreTrustedNodeContract.Repository {
    override fun connectToNode(address: String) {
        getResult("changeNodeAddress") {
            AppConfig.NODE_ADDRESS = address
            AppManager.instance.wallet?.changeNodeAddress(AppConfig.NODE_ADDRESS)
            PreferencesManager.putBoolean(PreferencesManager.KEY_CONNECT_TO_RANDOM_NODE, false)
            PreferencesManager.putString(PreferencesManager.KEY_NODE_ADDRESS, address)
        }
    }

    override fun getNodeConnectionStatusChanged(): Subject<Boolean> {
        return getResult(WalletListener.subOnNodeConnectedStatusChanged, "getNodeConnectionStatusChanged")
    }
}