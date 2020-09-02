package com.hds.hdswallet.screens.add_contact

import com.hds.hdswallet.base_screen.BasePresenter
import com.hds.hdswallet.core.AppManager
import com.hds.hdswallet.core.helpers.Tag
import com.hds.hdswallet.core.helpers.QrHelper
import com.hds.hdswallet.core.helpers.TagHelper
import io.reactivex.disposables.Disposable

class AddContactPresenter(view: AddContactContract.View?, repository: AddContactContract.Repository, private val state: AddContactState):
        BasePresenter<AddContactContract.View, AddContactContract.Repository>(view, repository), AddContactContract.Presenter {

    private var categorySubscription: Disposable? = null

    override fun initSubscriptions() {
        super.initSubscriptions()

        if (categorySubscription==null)
        {
            categorySubscription = TagHelper.subOnCategoryCreated.subscribe(){
                if (it!=null) {
                    state.tags = listOf<Tag>(it)
                }
            }
        }
    }

    override fun onDestroy() {
        categorySubscription?.dispose()

        super.onDestroy()
    }

    override fun onViewCreated() {
        super.onViewCreated()

        val address: String? = view?.getAddressFromArguments()
        if (!address.isNullOrBlank()) {
            view?.setAddress(address)
        }
    }

    override fun onCancelPressed() {
        view?.close()
    }


    override fun onStart() {
        super.onStart()

        val allTags = repository.getAllTags()

        view?.setupTagAction(allTags.isEmpty())

        view?.setTags(state.tags)
    }

    override fun onSavePressed() {
        val address = view?.getAddress() ?: ""
        val name = view?.getName() ?: ""

        if (QrHelper.isValidAddress(address)) {
            val oldAddress = AppManager.instance.getAddress(address)
            if (oldAddress!=null) {
                view?.showTokenError(oldAddress)
            }
            else{
                repository.saveContact(address, name.trim(), state.tags)
                view?.close()
            }
        } else {
            view?.showTokenError(null)
        }
    }

    override fun onCreateNewTagPressed() {
        view?.navigateToAddNewCategory()
    }

    override fun checkAddress() {
        val address = view?.getAddress() ?: ""
        val name = view?.getName() ?: ""

        if (QrHelper.isValidAddress(address)) {
            if (state.addresses.containsKey(address)) {
                view?.showTokenError(state.addresses[address])
            }
            else{
                view?.hideTokenError()
            }
        } else {
            view?.showTokenError(null)
        }
    }

    override fun onScannedQR(text: String?) {
        if (text == null) return

        val scannedAddress = QrHelper.getScannedAddress(text)

        if (QrHelper.isValidAddress(scannedAddress)) {
            view?.setAddress(scannedAddress)
        } else {
            view?.vibrate(100)
            view?.showErrorNotHdsAddress()
        }
    }

    override fun onSelectTags(tags: List<Tag>) {
        state.tags = tags
        view?.setTags(tags)
    }

    override fun onTagActionPressed() {
        if (repository.getAllTags().isEmpty()) {
            view?.showCreateTagDialog()
        } else {
            view?.showTagsDialog(state.tags)
        }
    }

    override fun onAddNewCategoryPressed() {
        view?.navigateToAddNewCategory()
    }

    override fun onScanPressed() {
        view?.navigateToScanQr()
    }

    override fun onTokenChanged() {
        view?.hideTokenError()
    }
}