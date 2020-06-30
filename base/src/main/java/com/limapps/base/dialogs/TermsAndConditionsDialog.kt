package com.limapps.base.dialogs

import android.app.Dialog
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.limapps.base.R
import com.limapps.base.others.Callback
import com.limapps.baseui.views.dialogs.BaseDialogFragment
import com.limapps.common.requireArguments

class TermsAndConditionsDialog : BaseDialogFragment<Callback<Any>>() {

    companion object {
        const val STORE_ETA = "storeETA"
        const val PROMISE_VALUE = "promiseValue"
        const val TERMS_AND_CONDITIONS = "termsAndConditions"

        fun newInstance(storeETA: String, termsAndConditions: String, promiseValue: String? = null): TermsAndConditionsDialog {
            val dialog = TermsAndConditionsDialog()
            dialog.arguments = Bundle().apply {
                putString(STORE_ETA, storeETA)
                putString(PROMISE_VALUE, promiseValue)
                putString(TERMS_AND_CONDITIONS, termsAndConditions)
            }
            return dialog
        }
    }

    private lateinit var currentView: View

    private val buttonOk by lazy { currentView.findViewById<Button>(R.id.button_ok) }
    private val buttonDismiss by lazy { currentView.findViewById<Button>(R.id.button_dismiss) }

    private val textViewEta: TextView by lazy { currentView.findViewById<TextView>(R.id.textView_eta) }
    private val textViewPromise: TextView by lazy { currentView.findViewById<TextView>(R.id.textView_promise) }
    private val textViewDescription: TextView by lazy { currentView.findViewById<TextView>(R.id.textView_description) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        currentView = inflater.inflate(R.layout.dialog_terms_and_conditions, container)
        return currentView
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonOk?.setOnClickListener { dismiss() }
        buttonDismiss?.setOnClickListener { dismiss() }

        textViewEta.text = getStoreETA()
        textViewPromise.text = getPromiseValue()
        textViewDescription.text = getTermsAndConditions()
        textViewDescription.movementMethod = ScrollingMovementMethod()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        buttonDismiss?.setOnClickListener(null)
        buttonOk?.setOnClickListener(null)
    }

    private fun getStoreETA() = requireArguments().getString(STORE_ETA).orEmpty().split(" ").first()

    private fun getPromiseValue(): String {
        val promiseValue = requireArguments().getString(PROMISE_VALUE)
        return if (promiseValue != null && promiseValue.isNotEmpty()) promiseValue else getString(R.string.terms_or_free)
    }

    private fun getTermsAndConditions() = requireArguments().getString(TERMS_AND_CONDITIONS)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Dialog(requireContext(), R.style.TermsAndConditionsDialog).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCanceledOnTouchOutside(false)
        }
    }
}