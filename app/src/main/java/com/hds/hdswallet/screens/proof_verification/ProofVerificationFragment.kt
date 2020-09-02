/*
 * // Copyright 2018 Hds Development
 * //
 * // Licensed under the Apache License, Version 2.0 (the "License");
 * // you may not use this file except in compliance with the License.
 * // You may obtain a copy of the License at
 * //
 * //    http://www.apache.org/licenses/LICENSE-2.0
 * //
 * // Unless required by applicable law or agreed to in writing, software
 * // distributed under the License is distributed on an "AS IS" BASIS,
 * // WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * // See the License for the specific language governing permissions and
 * // limitations under the License.
 */

package com.hds.hdswallet.screens.proof_verification

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.text.Editable
import android.text.InputType
import android.transition.AutoTransition
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.transition.TransitionManager
import com.hds.hdswallet.R
import com.hds.hdswallet.base_screen.BaseFragment
import com.hds.hdswallet.base_screen.BasePresenter
import com.hds.hdswallet.base_screen.MvpRepository
import com.hds.hdswallet.base_screen.MvpView
import com.hds.hdswallet.core.entities.PaymentProof
import com.hds.hdswallet.core.helpers.convertToHdsString
import com.hds.hdswallet.core.watchers.TextWatcher
import kotlinx.android.synthetic.main.fragment_proof_verification.*
import kotlinx.android.synthetic.main.fragment_proof_verification.btnDetailsCopy
import kotlinx.android.synthetic.main.fragment_proof_verification.receiverValue
import kotlinx.android.synthetic.main.fragment_proof_verification.senderValue
import kotlinx.android.synthetic.main.fragment_proof_verification.toolbarLayout
import kotlinx.android.synthetic.main.fragment_proof_verification.kernelValue
import kotlinx.android.synthetic.main.fragment_proof_verification.amountValue
import com.hds.hdswallet.core.AppManager
import kotlinx.android.synthetic.main.fragment_proof_verification.detailsArrowView
import kotlinx.android.synthetic.main.fragment_proof_verification.detailsExpandLayout
import kotlinx.android.synthetic.main.fragment_proof_verification.detailsLayout
import kotlinx.android.synthetic.main.fragment_proof_verification.kernelLayout
import kotlinx.android.synthetic.main.fragment_proof_verification.amountLayout
import kotlinx.android.synthetic.main.fragment_proof_verification.senderLayout
import kotlinx.android.synthetic.main.fragment_proof_verification.receiverLayout
import android.view.inputmethod.EditorInfo
import android.view.KeyEvent
import com.hds.hdswallet.core.App




class ProofVerificationFragment : BaseFragment<ProofVerificationPresenter>(), ProofVerificationContract.View {
    private lateinit var textWatcher: TextWatcher

    override fun onControllerGetContentLayoutId(): Int = R.layout.fragment_proof_verification

    override fun getToolbarTitle(): String? = getString(R.string.payment_proof_verification)

    override fun getStatusBarColor(): Int = if (App.isDarkMode) {
    ContextCompat.getColor(context!!, R.color.addresses_status_bar_color_black)
}
else{
    ContextCompat.getColor(context!!, R.color.addresses_status_bar_color)
}

    override fun addListeners() {
        textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                presenter?.onProofCodeChanged(s.toString())

                if (s.toString().isNullOrEmpty())
                {
                    var typeFace: Typeface? = ResourcesCompat.getFont(context!!, R.font.roboto_italic)
                    proofValue.typeface = typeFace
                }
                else{
                    var typeFace: Typeface? = ResourcesCompat.getFont(context!!, R.font.roboto_regular)
                    proofValue.typeface = typeFace
                }
            }
        }

        proofValue.addListener(textWatcher)

        proofValue.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                hideKeyboard()
                true
            } else {
                false
            }
        }


        btnDetailsCopy.setOnClickListener {
            presenter?.onCopyDetailsPressed()
        }

        detailsExpandLayout.setOnClickListener {
            presenter?.onExpandDetailsPressed()
        }
    }

    override fun init() {
        toolbarLayout.hasStatus = true

        proofValue.imeOptions = EditorInfo.IME_ACTION_DONE
        proofValue.setRawInputType(InputType.TYPE_CLASS_TEXT)

      //  proofValue.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS and InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE)

        var typeFace: Typeface? = ResourcesCompat.getFont(context!!, R.font.roboto_italic)
        proofValue.typeface = typeFace

        proofValue.requestFocus()
        showKeyboard()

    }

    override fun clear() {
        TransitionManager.beginDelayedTransition(proofContainer)
        detailsLayout.visibility = View.GONE
        btnDetailsCopy.visibility = View.GONE
    }

    override fun showErrorProof() {
        proofError.visibility = View.VISIBLE
        view4.setBackgroundColor(context!!.getColor(R.color.common_error_color))

     //   val errorColorStateList = ColorStateList.valueOf(context!!.getColor(R.color.common_error_color))
       // proofValue.backgroundTintList = errorColorStateList
        proofValue.setTextColor(context!!.getColor(R.color.common_error_color))
    }

    override fun hideErrorProof() {
        proofError.visibility = View.GONE
        view4.setBackgroundColor(context!!.getColor(R.color.white_02))

       // proofValue.backgroundTintList = ColorStateList.valueOf(context!!.getColor(R.color.white_01))
        proofValue.setTextColor(context!!.getColor(R.color.common_text_color))
    }

    @SuppressLint("SetTextI18n")
    override fun showProof(proof: PaymentProof) {
        proofValue.clearFocus()
        hideKeyboard()
        btnDetailsCopy.requestFocus()

        senderValue.text = proof.senderId
        receiverValue.text = proof.receiverId

        val sender = AppManager.instance.getAddress(proof.senderId)
        if(sender !=null && !sender.label.isNullOrEmpty())
        {
            senderContactLayout.visibility = View.VISIBLE
            senderContactValue.text = sender.label
        }
        else{
            senderContactLayout.visibility = View.GONE
        }

        val receiver = AppManager.instance.getAddress(proof.receiverId)
        if(receiver !=null && !receiver.label.isNullOrEmpty())
        {
            receiverContactLayout.visibility = View.VISIBLE
            receiverContactValue.text = receiver.label
        }
        else{
            receiverContactLayout.visibility = View.GONE
        }


        amountValue.text = "${proof.amount.convertToHdsString()} ${getString(R.string.currency_hds)}".toUpperCase()
        kernelValue.text = proof.kernelId

        TransitionManager.beginDelayedTransition(proofContainer)
        detailsLayout.visibility = View.VISIBLE
        btnDetailsCopy.visibility = View.VISIBLE

        presenter?.setShouldExpandDetail(true)
    }

    override fun showCopiedMessage() {
        showSnackBar(getString(R.string.copied))
    }

    override fun getDetailsContent(proof: PaymentProof): String {
        return "${getString(R.string.sender)} " +
                "${proof.senderId} \n" +
                "${getString(R.string.receiver)} " +
                "${proof.receiverId} \n" +
                "${getString(R.string.amount)} " +
                "${(proof.amount.convertToHdsString() + getString(R.string.currency_hds)).toUpperCase()} \n" +
                "${getString(R.string.kernel_id)} " +
                proof.kernelId
    }

    override fun handleExpandDetails(shouldExpandDetails: Boolean) {
        animateDropDownIcon(detailsArrowView, !shouldExpandDetails)

        android.transition.TransitionManager.beginDelayedTransition(proofContainer, AutoTransition().apply {
        })

        val contentVisibility = if (shouldExpandDetails) View.VISIBLE else View.GONE
        receiverLayout.visibility = contentVisibility
        senderLayout.visibility = contentVisibility
        kernelLayout.visibility = contentVisibility
        amountLayout.visibility = contentVisibility
    }

    override fun clearListeners() {
        btnDetailsCopy.setOnClickListener(null)
        detailsExpandLayout.setOnClickListener(null)
    }

    override fun initPresenter(): BasePresenter<out MvpView, out MvpRepository> {
        return ProofVerificationPresenter(this, ProofVerificationRepository(), ProofVerificationState())
    }

    private fun animateDropDownIcon(view: View, shouldExpand: Boolean) {
        val angleFrom = if (shouldExpand) 180f else 360f
        val angleTo = if (shouldExpand) 360f else 180f
        val anim = ObjectAnimator.ofFloat(view, "rotation", angleFrom, angleTo)
        anim.duration = 500
        anim.start()
    }
}