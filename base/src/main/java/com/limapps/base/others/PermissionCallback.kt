package com.limapps.base.others

interface PermissionCallback {
    fun onRequestPermissionResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray)
}

data class PermissionResult(val permission: String,
                            val grantResult: PermissionState)

enum class PermissionState {
    GRANTED, DENIED, COMPLETELY_DENIED
}
