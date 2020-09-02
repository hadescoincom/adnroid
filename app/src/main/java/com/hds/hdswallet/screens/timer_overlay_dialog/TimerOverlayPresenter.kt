package com.hds.hdswallet.screens.timer_overlay_dialog

import com.hds.hdswallet.base_screen.BasePresenter
import com.hds.hdswallet.core.helpers.DelayedTask

class TimerOverlayPresenter(view: TimerOverlayContract.View?, repository: TimerOverlayContract.Repository)
    : BasePresenter<TimerOverlayContract.View, TimerOverlayContract.Repository>(view, repository), TimerOverlayContract.Presenter {


    override fun onViewCreated() {
        super.onViewCreated()
        view?.init()
    }
}