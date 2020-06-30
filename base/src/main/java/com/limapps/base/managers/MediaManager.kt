package com.limapps.base.managers

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import com.limapps.base.activities.BaseFragmentActivity
import com.limapps.base.others.CallbackAdapter
import com.limapps.common.isLollipop

object MediaManager {

    const val REQUEST_IMAGE_CAPTURE = 123
    const val REQUEST_LOAD_IMAGE = 124

    fun loadCamera(activity: BaseFragmentActivity, folderType: Int, fragment: androidx.fragment.app.Fragment? = null) {
        activity.validateCameraPermission(object : CallbackAdapter<Unit>() {
            override fun onResolve(resolved: Unit) {
                loadCameraIntent(activity, folderType, fragment)
            }
        }, cameraPermissions = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE))
    }

    fun loadGalleryImage(activity: BaseFragmentActivity, fragment: androidx.fragment.app.Fragment? = null) {
        activity.validateStoragePermission(object : CallbackAdapter<Unit>() {
            override fun onResolve(resolved: Unit) {
                loadGalleryIntent(activity, fragment)
            }

            override fun onReject(error: Throwable?) {
                loadGalleryImage(activity, fragment)
            }
        })
    }

    @Deprecated("Use loadGalleryIntent() with the right parameters",
            ReplaceWith("loadGalleryIntent(Activity, Int) or loadGalleryIntent(Fragment, Int)"))
    fun loadGalleryIntent(activity: Activity, fragment: androidx.fragment.app.Fragment?) {
        if (fragment != null) {
            loadGalleryIntent(fragment)
        } else {
            loadGalleryIntent(activity)
        }

    }

    fun loadGalleryIntent(activity: Activity) {
        val pickImageIntent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        if (pickImageIntent.resolveActivity(activity.packageManager) != null) {
            activity.startActivityForResult(pickImageIntent, REQUEST_LOAD_IMAGE)
        }
    }

    fun loadGalleryIntent(fragment: androidx.fragment.app.Fragment) {
        val pickImageIntent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        if (pickImageIntent.resolveActivity(fragment.requireContext().packageManager) != null) {
            fragment.startActivityForResult(pickImageIntent, REQUEST_LOAD_IMAGE)
        }
    }


    @Deprecated("Use loadCameraIntent() with the right parameters",
            ReplaceWith("loadCameraIntent(Activity, Int) or loadCameraIntent(Fragment, Int)"))
    private fun loadCameraIntent(activity: Activity, folderType: Int, fragment: androidx.fragment.app.Fragment?) {
        if (fragment != null) {
            loadCameraIntent(fragment, folderType)
        } else {
            loadCameraIntent(activity, folderType)
        }
    }

    fun loadCameraIntent(activity: Activity, folderType: Int) {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        }

        if (folderType != FileManager.NO_FILE) {
            val uri = FileManager.generateUriForPicture(activity, folderType, activity.applicationContext.packageName)
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri)

            if (!isLollipop()) {
                val resInfoList = activity.packageManager.queryIntentActivities(takePictureIntent, PackageManager.MATCH_DEFAULT_ONLY)
                for (resolveInfo in resInfoList) {
                    val packageName = resolveInfo.activityInfo.packageName
                    activity.grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                }

            }
        }

        activity.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
    }

    fun loadCameraIntent(fragment: androidx.fragment.app.Fragment, folderType: Int) {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        }

        if (folderType != FileManager.NO_FILE) {
            val uri = FileManager.generateUriForPicture(fragment.requireContext(), folderType, fragment.requireContext().packageName)
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri)

            if (!isLollipop()) {
                val resInfoList = fragment.requireContext().packageManager.queryIntentActivities(takePictureIntent, PackageManager.MATCH_DEFAULT_ONLY)
                for (resolveInfo in resInfoList) {
                    val packageName = resolveInfo.activityInfo.packageName
                    fragment.requireContext().grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                }

            }
        }

        fragment.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
    }
}
