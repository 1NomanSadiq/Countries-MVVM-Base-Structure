package com.gsc.app.utils.permissions

interface PermissionHandler {
    fun onGranted()
    fun onDenied()
}