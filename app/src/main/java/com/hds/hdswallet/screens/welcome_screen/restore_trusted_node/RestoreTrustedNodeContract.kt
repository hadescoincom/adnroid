package com.hds.hdswallet.screens.welcome_screen.restore_trusted_node

import com.hds.hdswallet.base_screen.MvpPresenter
import com.hds.hdswallet.base_screen.MvpRepository
import com.hds.hdswallet.base_screen.MvpView
import io.reactivex.subjects.Subject

interface RestoreTrustedNodeContract {

    interface View: MvpView {
        fun init()
        fun getNodeAddress(): String
        fun showLoading()
        fun dismissLoading()
        fun showError()
        fun navigateToProgress()
    }

    interface Presenter: MvpPresenter<View> {
        fun onNextPressed()
    }

    interface Repository : MvpRepository {
        fun connectToNode(address: String)
        fun getNodeConnectionStatusChanged(): Subject<Boolean>
    }
}