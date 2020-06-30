package com.limapps.base.fragments

import android.app.Dialog
import androidx.fragment.app.DialogFragment
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import com.limapps.base.R
import com.limapps.baseui.others.OnClickViewListener

class MaxLengthCharDialogFragment : androidx.fragment.app.DialogFragment() {

    companion object {
        fun newInstance(): MaxLengthCharDialogFragment = MaxLengthCharDialogFragment()
    }

    override fun onActivityCreated(arg0: Bundle?) {
        if (dialog == null) {
            showsDialog = false
        }
        super.onActivityCreated(arg0)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog = Dialog(context!!)
        dialog.setCanceledOnTouchOutside(false)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        val dialogView = activity!!.layoutInflater.inflate(R.layout.dialog_max_char_length, null)
        dialog.setContentView(dialogView)
        dialog.window?.let { configureDialogWindow(it) }

        initListeners(dialogView, dialog)

        return dialog
    }

    private fun configureDialogWindow(window: Window) {
        window.apply {
            attributes.windowAnimations = 0
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH)
            setFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM, WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
        }
    }

    private fun initListeners(view: View, dialog: Dialog) {
        view.findViewById<View>(R.id.button_understood).setOnClickListener(object : OnClickViewListener() {
            override fun onClickView(view: View) {
                dismiss()
            }
        })

        dialog.setOnKeyListener { _, keyCode, keyEvent ->
            if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent.action == KeyEvent.ACTION_UP) {
                dismiss()
                true
            } else {
                false
            }
        }
    }

}
