package com.limapps.base.models

data class NotificationControl(val notificationType: String = "",
                               val description: String = "",
                               var status: Boolean = true)