package com.limapps.baseui.views.dialogs

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.limapps.baseui.R
import com.limapps.baseui.interfaces.ConfirmationBottomCallback
import com.limapps.baseui.others.OnClickViewListener
import com.limapps.baseui.animations.appearFromBottom
import com.limapps.baseui.animations.disappearToBottom
import com.limapps.common.hide
import com.limapps.common.show

class BottomConfirmFragment : BaseDialogFragment<ConfirmationBottomCallback>() {

    private var layoutContainer: LinearLayout? = null
    private var imageViewIcon: ImageView? = null
    private var buttonAlertOptionOne: Button? = null
    private var buttonAlertOptionTwo: Button? = null
    private var textViewBoldTitle: TextView? = null
    private var textViewTitle: TextView? = null
    private var textViewCancel: TextView? = null
    private var textViewTag: TextView? = null

    companion object {

        const val ICON_RESOURCE = "icon_resource"
        const val TEXT_TITLE = "text_title"
        const val TEXT_HEADER = "text_header"
        const val TEXT_BUTTON_ONE = "text_button_one"
        const val TEXT_BUTTON_TWO = "text_button_two"
        const val TAG_NAME = "tag_name"
        const val CANCEL = "CANCEL"
        const val DIALOG_CONTENT_DESCRIPTION = "DIALOG_CONTENT_DESCRIPTION"

        fun newInstance(bundleOptions: Bundle, confirmationBottomCallback: ConfirmationBottomCallback): BottomConfirmFragment {
            val bottomConfirmFragment = BottomConfirmFragment()
            bottomConfirmFragment.arguments = bundleOptions
            bottomConfirmFragment.setCallback(confirmationBottomCallback)
            return bottomConfirmFragment
        }
    }

    override fun onActivityCreated(arg0: Bundle?) {
        showsDialog = dialog != null
        super.onActivityCreated(arg0)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = context?.let { Dialog(it) }
        dialog?.setCanceledOnTouchOutside(true)

        dialog?.window?.attributes?.windowAnimations = 0
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.layout_bottom_confirmation, null)

        dialog?.setContentView(dialogView)
        val window = dialog?.window
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL)
        window.setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH)
        window.setFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM, WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)

        dialog.setOnKeyListener { _, keyCode, event ->
            return@setOnKeyListener if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
                close()
                true
            } else {
                false
            }
        }

        initViews(dialogView)
        setListeners()
        buildView()
        show()
        return dialog
    }

    private fun initViews(view: View) {
        imageViewIcon = view.findViewById(R.id.imageView_confirm)
        textViewBoldTitle = view.findViewById(R.id.rappi_confirm_textView_header)
        textViewTitle = view.findViewById(R.id.textView_title)
        textViewCancel = view.findViewById(R.id.textView_cancel)
        textViewTag = view.findViewById(R.id.rappi_confirm_textView_tag)
        buttonAlertOptionOne = view.findViewById(R.id.button_alert_option_one)
        buttonAlertOptionTwo = view.findViewById(R.id.button_alert_option_two)
        layoutContainer = view.findViewById(R.id.layout_container)
    }

    private fun setListeners() {
        val listener = object : OnClickViewListener() {
            override fun onClickView(view: View) {
                callback?.let {
                    when (view.id) {
                        R.id.button_alert_option_one -> it.onYesOption()
                        R.id.button_alert_option_two -> it.onNoOption()
                    }
                    close()
                }
            }
        }

        buttonAlertOptionOne?.setOnClickListener(listener)
        buttonAlertOptionTwo?.setOnClickListener(listener)
        textViewCancel?.setOnClickListener(listener)
    }

    private fun buildView() {
        textViewTitle?.text = arguments?.getString(TEXT_HEADER)
        buttonAlertOptionOne?.text = arguments?.getString(TEXT_BUTTON_ONE)
        buttonAlertOptionTwo?.text = arguments?.getString(TEXT_BUTTON_TWO)

        if (arguments?.getBoolean(CANCEL) == true) {
            textViewCancel?.show()
        } else {
            textViewCancel?.hide()
        }

        val textTitle = arguments?.getString(TEXT_TITLE)
        if (!textTitle.isNullOrEmpty()) {
            textViewBoldTitle?.show()
            textViewBoldTitle?.text = textTitle
        } else {
            textViewBoldTitle?.hide()
        }

        val iconResource = arguments?.getInt(ICON_RESOURCE)
        if (iconResource != 0) {
            iconResource?.let {
                imageViewIcon?.show()
                imageViewIcon?.setImageResource(it)
            }
        } else {
            imageViewIcon?.hide()
        }

        val tagName = arguments?.getString(TAG_NAME)
        if (!tagName.isNullOrEmpty()) {
            textViewTag?.show()
            textViewTag?.text = tagName
        } else {
            textViewTag?.hide()
        }
        layoutContainer?.contentDescription = arguments?.getString(DIALOG_CONTENT_DESCRIPTION).orEmpty()
    }

    fun show() {
        //TODO find a better way using the background of the dialogFragment
        layoutContainer?.appearFromBottom(300, 200)
    }

    fun close() {
        animateOut()
    }

    private fun animateOut() {
        layoutContainer?.disappearToBottom(200, 0, object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                try {
                    super@BottomConfirmFragment.dismiss()
                } catch (exception: Exception) {
                    exception.printStackTrace()
                }
            }
        })
    }

    fun setConfirmationCallback(confirmationCallback: ConfirmationBottomCallback) {
        this.callback = confirmationCallback
    }
}
