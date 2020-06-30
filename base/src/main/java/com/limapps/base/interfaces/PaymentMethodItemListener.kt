package com.limapps.base.interfaces

import android.view.View
import com.limapps.base.checkout.utils.PaymentType

interface PaymentMethodItemListener {
    fun onClickItemSelected(type: PaymentType, paymentMethodView: View)
    fun unselectedMethod(type: PaymentType, paymentMethodView: View)
}