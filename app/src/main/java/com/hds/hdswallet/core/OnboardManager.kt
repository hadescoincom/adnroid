package com.hds.hdswallet.core

import com.hds.hdswallet.core.helpers.PreferencesManager
import com.hds.hdswallet.core.helpers.convertToHds
import java.util.prefs.Preferences

class OnboardManager {

    companion object {
        private var INSTANCE: OnboardManager? = null

        val instance: OnboardManager
            get() {
                if (INSTANCE == null) {
                    INSTANCE = OnboardManager()
                }

                return INSTANCE!!
            }
    }

    private val minAmountToSecure:Double = 10.0

    var isCloseSecure = false
    var isCloseFaucet = false

    fun reset() {
        isCloseSecure = false
        isCloseFaucet = false

        PreferencesManager.putBoolean(PreferencesManager.KEY_SEED_IS_SKIP, false)
    }

    fun isSkipedSeed(): Boolean {
        return PreferencesManager.getBoolean(PreferencesManager.KEY_SEED_IS_SKIP,false)
    }

    fun getSeed():String? {
        return PreferencesManager.getString(PreferencesManager.KEY_SEED)
    }

    fun canReceiveFaucet(): Boolean {

        val isInProgress = AppManager.instance.getStatus().sending > 0 || AppManager.instance.getStatus().receiving > 0
        val isBalanceZero = AppManager.instance.getStatus().available == 0L
        val isTransactionsEmpty = AppManager.instance.getTransactions().count() == 0
        return !isInProgress && isBalanceZero && !isCloseFaucet && isTransactionsEmpty
    }

    fun canMakeSecure(): Boolean {
        val available = AppManager.instance.getStatus().available

        if (available.convertToHds() >= minAmountToSecure && !isCloseSecure && isSkipedSeed())
        {
            return  true
        }

        return  false
    }

    fun makeSecure() {
        PreferencesManager.putBoolean(PreferencesManager.KEY_SEED_IS_SKIP, false)
    }


}