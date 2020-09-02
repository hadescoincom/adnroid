package com.hds.hdswallet.screens.timer_overlay_dialog

import com.hds.hdswallet.base_screen.MvpPresenter
import com.hds.hdswallet.base_screen.MvpRepository
import com.hds.hdswallet.base_screen.MvpView

interface TimerOverlayContract {
    interface View: MvpView {
        fun init()
    }

    interface Presenter: MvpPresenter<View> {
    }

    interface Repository: MvpRepository {

    }
}