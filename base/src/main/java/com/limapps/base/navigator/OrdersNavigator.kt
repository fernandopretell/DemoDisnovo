package com.limapps.base.navigator

import android.content.Intent
import com.limapps.base.chat.CHAT_STOREKEEPER
import com.limapps.base.chat.CHAT_SUPPORT
import com.limapps.base.others.ExtrasKeys
import com.limapps.base.others.getChatIntent
import com.limapps.base.others.getNewOrderInProgressIntent
import com.limapps.base.others.newOrderScheduledIntent
import javax.inject.Inject

class OrdersNavigator @Inject constructor() {

    fun getEtaIntent(orderId: String,
                     opensChat: Boolean = false,
                     opensSupport: Boolean = false): Intent {
        return getNewOrderInProgressIntent(orderId, opensChat, opensSupport)
    }

    fun getScheduledIntent(orderId: String): Intent {
        return newOrderScheduledIntent(orderId)
    }

    fun getSupportChatIntent(orderId: String, chatType: String, supportRating: String): Intent {
        return getChatIntent()
                .apply {
                    putExtra(ExtrasKeys.ORDER_ID, orderId)
                    putExtra(ExtrasKeys.CHAT_TYPE, chatType)
                    putExtra(ExtrasKeys.SUPPORT_RATING, supportRating)
                }
    }
}