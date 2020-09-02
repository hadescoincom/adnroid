package com.hds.hdswallet.screens.welcome_screen.restore_owner_key

import com.hds.hdswallet.base_screen.MvpPresenter
import com.hds.hdswallet.base_screen.MvpRepository
import com.hds.hdswallet.base_screen.MvpView
import com.hds.hdswallet.core.helpers.Status
import com.hds.hdswallet.core.helpers.WelcomeMode

interface RestoreOwnerKeyContract {

    interface View: MvpView {
        fun init(key: String)
        fun getPassword(): String
        fun getSeed(): Array<String>
        fun showCopiedSnackBar()
        fun navigateToEnterTrustedNode()
    }

    interface Presenter: MvpPresenter<View> {
        fun onCopyPressed()
        fun onNextPressed()
    }

    interface Repository: MvpRepository {
        fun getOwnerKey(pass: String): String
        fun createWallet(pass: String?, seed: String?): Status
    }
}