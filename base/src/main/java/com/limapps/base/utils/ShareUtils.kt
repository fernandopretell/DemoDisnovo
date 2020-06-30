package com.limapps.base.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.telephony.PhoneNumberUtils
import com.facebook.share.model.ShareLinkContent
import com.facebook.share.widget.ShareDialog
import com.limapps.base.RappiApplication
import com.limapps.common.tryOrDefault

object ShareUtils {

    const val FACEBOOK = "Facebook"
    const val MESSENGER = "Messenger"
    const val TWITTER = "Twitter"
    const val WHATSAPP = "WhatsApp"
    const val INSTAGRAM = "INSTAGRAM"
    const val EMAIL = "Email"
    const val SMS = "Sms"

    private val WHATSAPP_PACKAGE_NAME = "com.whatsapp"
    private val MESSENGER_PACKAGE_NAME = "com.facebook.orca"
    private val TWITTER_PACKAGE_NAME = "com.twitter.android"
    private val INSTAGRAM_PACKAGE_NAME = "com.instagram.android"
    private val PARAM_TEXT_PLAIN = "text/plain"
    private val PARAM_IMAGE = "image/*"

    //private val packageManager by lazy { RappiApplication.getInstance().packageManager }

    /*fun shareThroughChooser(context: Context, title: String, message: String, packageName: String = "") {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            type = PARAM_TEXT_PLAIN
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            putExtra(Intent.EXTRA_TEXT, message)
            putExtra(Intent.EXTRA_SUBJECT, title)
            if (validatePackageName(packageName)) {
                `package` = packageName
            }
        }

        val intentToStart = Intent.createChooser(shareIntent, title).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }

        context.startActivity(intentToStart)
    }*/

    /*fun shareThroughSmsProvider(context: Context, phoneList: List<String>, message: String) {
        val smsIntent = Intent().apply {
            action = Intent.ACTION_SENDTO
            type = PARAM_TEXT_PLAIN
            data = Uri.parse("smsto:${phoneList.joinToString(separator = ",")}")
            putExtra("sms_body", message)
        }
        if (smsIntent.resolveActivity(packageManager) != null) {
            context.startActivity(smsIntent)
        }
    }*/

    /*fun shareThroughEmailProvider(context: Context, title: String, message: String) {
        val emailIntent = Intent().apply {
            action = Intent.ACTION_SENDTO
            type = PARAM_TEXT_PLAIN
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_SUBJECT, title)
            putExtra(Intent.EXTRA_TEXT, message)
        }
        if (emailIntent.resolveActivity(packageManager) != null) {
            context.startActivity(emailIntent)
        }
    }*/

    /*fun shareThroughWhatsAppNumber(context: Context, message: String, countryCode: String, number: String) {
        val parsedPhoneNumber = PhoneNumberUtils.formatNumber(number, countryCode)
        parsedPhoneNumber?.let {
            val formattedPhoneNumber = parsedPhoneNumber.removePrefix("+").replace(" ", "").replace("-", "")
            val shareIntent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://api.whatsapp.com/send?phone=$formattedPhoneNumber&text=$message")
            }
            context.startActivity(shareIntent)
        } ?: shareThroughWhatsApp(context, "", message)
    }*/

   /* fun shareThroughWhatsApp(context: Context, title: String, message: String) {
        shareThroughChooser(context, title, message, WHATSAPP_PACKAGE_NAME)
    }

    fun shareThroughFacebook(activity: Activity, url: String, message: String = "") {
        ShareDialog.show(activity, ShareLinkContent.Builder().setQuote(message).setContentUrl(Uri.parse(url)).build())
    }

    fun shareThroughTwitter(context: Context, title: String, message: String) {
        shareThroughChooser(context, title, message, TWITTER_PACKAGE_NAME)
    }

    fun shareThroughMessenger(context: Context, title: String, message: String) {
        shareThroughChooser(context, title, message, MESSENGER_PACKAGE_NAME)
    }

    fun shareThroughInstagram(context: Context, title: String, message: String) {
        shareThroughChooser(context, title, message, INSTAGRAM_PACKAGE_NAME)
    }*/

    fun shareImageThroughChooser(context: Context, shareFileUri: Uri, shareTitle: String, description: String = "") {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            type = PARAM_IMAGE
            putExtra(Intent.EXTRA_TEXT, description)
            putExtra(Intent.EXTRA_SUBJECT, shareTitle)
            putExtra(Intent.EXTRA_STREAM, shareFileUri)
        }

        val intentToStart = Intent.createChooser(shareIntent, shareTitle).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_GRANT_READ_URI_PERMISSION
        }

        context.startActivity(intentToStart)
    }

    /*private fun validatePackageName(packageName: String): Boolean =
            tryOrDefault({
                packageManager?.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
                true
            }, false)*/
}
