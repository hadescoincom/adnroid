package com.hds.hdswallet.screens.welcome_screen.restore_mode_choice

import com.hds.hdswallet.base_screen.MvpPresenter
import com.hds.hdswallet.base_screen.MvpRepository
import com.hds.hdswallet.base_screen.MvpView

interface RestoreModeChoiceContract {
    interface View: MvpView {
        fun getPassword(): String
        fun getSeed(): Array<String>
        fun showAutoRestoreWarning()
        fun showNodeRestoreWarning()
        fun showRestoreOwnerKey(pass: String, seed: Array<String>)
        fun showAutomaticProgressRestore(pass: String, seed: Array<String>)
    }
    interface Presenter: MvpPresenter<View> {
        fun onNextPressed(isAutomaticRestore: Boolean)
        fun onConfirmRestorePressed(isAutomaticRestore: Boolean)
    }
    interface Repository: MvpRepository {
        fun removeWallet()
        fun saveStartRestoreFlag()
    }
}