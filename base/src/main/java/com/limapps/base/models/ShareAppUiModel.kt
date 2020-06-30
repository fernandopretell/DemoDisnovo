package com.limapps.base.models

sealed class ShareAppUiModel {

    class whatsApp(val title: String, val message: String) : ShareAppUiModel()

    class facebook(val url: String, val message: String = "") : ShareAppUiModel()

    class twitter(val title: String, val message: String = "") : ShareAppUiModel()

    class instagram(val title: String, val message: String = "") : ShareAppUiModel()

    class otherApp(val title: String, val message: String) : ShareAppUiModel()
}