package com.hds.hdswallet.screens.add_contact

import com.hds.hdswallet.base_screen.MvpPresenter
import com.hds.hdswallet.base_screen.MvpRepository
import com.hds.hdswallet.base_screen.MvpView
import com.hds.hdswallet.core.entities.OnAddressesData
import com.hds.hdswallet.core.entities.OnTxStatusData
import com.hds.hdswallet.core.entities.TxDescription
import com.hds.hdswallet.core.entities.WalletAddress
import com.hds.hdswallet.core.helpers.Tag
import com.hds.hdswallet.core.helpers.TrashManager
import io.reactivex.Observable
import io.reactivex.subjects.Subject

interface AddContactContract {
    interface View : MvpView {
        fun getAddress(): String
        fun getName(): String
        fun showTokenError(address:WalletAddress?)
        fun hideTokenError()
        fun close()
        fun navigateToScanQr()
        fun navigateToAddNewCategory()
        fun setAddress(address: String)
        fun setupTagAction(isEmptyTags: Boolean)
        fun showTagsDialog(selectedTags: List<Tag>)
        fun showCreateTagDialog()
        fun setTags(tags: List<Tag>)
        fun showErrorNotHdsAddress()
        fun getAddressFromArguments(): String?
    }

    interface Presenter : MvpPresenter<View> {
        fun checkAddress()
        fun onTokenChanged()
        fun onCancelPressed()
        fun onAddNewCategoryPressed()
        fun onSavePressed()
        fun onScanPressed()
        fun onScannedQR(text: String?)
        fun onTagActionPressed()
        fun onSelectTags(tags: List<Tag>)
        fun onCreateNewTagPressed()
    }

    interface Repository: MvpRepository {
        fun saveContact(address: String, name: String, tags: List<Tag>)
        fun getAddressTags(address: String): List<Tag>
        fun getAllTags(): List<Tag>
    }
}