package com.limapps.baseui.views.dialogs

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField

class ConfirmationDialogModel {
    val title = ObservableField<String>()
    val deepLinkText = ObservableField<String>()
    val description = ObservableField<String>()
    val primaryButtonText = ObservableField<String>()
    val secondaryButtonText = ObservableField<String>()
    val message = ObservableField<String>()
    val hasDescription = ObservableBoolean()
    val hasDeepLink = ObservableBoolean()
    val hasSecondaryButton = ObservableBoolean()
    val hasIcon = ObservableBoolean()
}