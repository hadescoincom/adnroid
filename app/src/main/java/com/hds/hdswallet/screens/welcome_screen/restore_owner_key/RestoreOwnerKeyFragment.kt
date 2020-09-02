package com.hds.hdswallet.screens.welcome_screen.restore_owner_key

import android.view.View
import androidx.navigation.fragment.findNavController
import com.hds.hdswallet.R
import com.hds.hdswallet.base_screen.BaseFragment
import com.hds.hdswallet.base_screen.BasePresenter
import com.hds.hdswallet.base_screen.MvpRepository
import com.hds.hdswallet.base_screen.MvpView
import kotlinx.android.synthetic.main.fragment_restore_owner_key.*

class RestoreOwnerKeyFragment: BaseFragment<RestoreOwnerKeyPresenter>(), RestoreOwnerKeyContract.View {

    private val args by lazy {
        RestoreOwnerKeyFragmentArgs.fromBundle(arguments!!)
    }

    override fun getPassword(): String = args.pass

    override fun getSeed(): Array<String> = args.seed

    override fun onControllerGetContentLayoutId(): Int = R.layout.fragment_restore_owner_key

    override fun getToolbarTitle(): String? = getString(R.string.owner_key)

    override fun init(key: String) {
        progressBar.visibility = View.GONE

        ownerKey.visibility = View.VISIBLE
        ownerKey.text = key
    }

    override fun addListeners() {
        btnCopy.setOnClickListener {
            presenter?.onCopyPressed()
        }

        btnNext.setOnClickListener {
            presenter?.onNextPressed()
        }
    }

    override fun clearListeners() {
        btnCopy.setOnClickListener(null)
        btnNext.setOnClickListener(null)
    }

    override fun showCopiedSnackBar() {
        showSnackBar(getString(R.string.owner_key_copied_message))
    }

    override fun navigateToEnterTrustedNode() {
        findNavController().navigate(RestoreOwnerKeyFragmentDirections.actionRestoreOwnerKeyFragmentToRestoreTustedNodeFragment())
    }

    override fun initPresenter(): BasePresenter<out MvpView, out MvpRepository> {
        return RestoreOwnerKeyPresenter(this, RestoreOwnerKeyRepository())
    }

}